package im.fooding.core.service.naverplace;

import im.fooding.core.common.PageResponse;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.naverplace.NaverPlace;
import im.fooding.core.model.region.Region;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.StoreCategory;
import im.fooding.core.model.store.StoreStatus;
import im.fooding.core.model.user.User;
import im.fooding.core.repository.naverplace.NaverPlaceRepository;
import im.fooding.core.service.store.StoreService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NaverPlaceService {

    private final NaverPlaceRepository naverPlaceRepository;
    private final StoreService storeService;

    @Transactional
    public String create(
            String id,
            String name,
            String category,
            String address,
            String contact,
            List<NaverPlace.Menu> menus
    ) {
        NaverPlace naverPlace = NaverPlace.builder()
                .id(id)
                .name(name)
                .category(category)
                .address(address)
                .contact(contact)
                .menus(menus)
                .build();

        return naverPlaceRepository.save(naverPlace)
                .getId();
    }

    public Page<NaverPlace> getNaverPlaces(Pageable pageable, Boolean isUploaded) {
        if (isUploaded == null) {
            return naverPlaceRepository.findAll(pageable);
        }
        
        // MongoDB에서 동적 필터링을 위해 모든 데이터를 가져온 후 필터링
        Page<NaverPlace> allPlaces = naverPlaceRepository.findAll(pageable);
        
        if (isUploaded) {
            // 업로드된 것만 필터링
            List<NaverPlace> filteredPlaces = allPlaces.getContent().stream()
                    .filter(NaverPlace::isUploaded)
                    .toList();
            
            return new PageImpl<>(filteredPlaces, pageable, filteredPlaces.size());
        } else {
            // 업로드되지 않은 것만 필터링
            List<NaverPlace> filteredPlaces = allPlaces.getContent().stream()
                    .filter(place -> !place.isUploaded())
                    .toList();
            
            return new PageImpl<>(filteredPlaces, pageable, filteredPlaces.size());
        }
    }

    /**
     * ID로 NaverPlace 조회
     */
    public Optional<NaverPlace> findById(String id) {
        return naverPlaceRepository.findById(id);
    }
    
    /**
     * NaverPlace 정보를 바탕으로 Store를 생성하고 isUploaded를 true로 설정
     * 
     * @param naverPlaceId NaverPlace ID
     * @param owner Store 소유자
     * @param region Store 지역
     * @return 생성된 Store ID
     * @throws ApiException 이미 업로드된 경우 또는 NaverPlace를 찾을 수 없는 경우
     */
    @Transactional
    public Long upload(String naverPlaceId, User owner, Region region) {
        NaverPlace naverPlace = naverPlaceRepository.findById(naverPlaceId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "NaverPlace를 찾을 수 없습니다: " + naverPlaceId));
        
        if (naverPlace.isUploaded()) {
            throw new ApiException(ErrorCode.STORE_NOT_FOUND, "이미 업로드된 NaverPlace입니다: " + naverPlaceId);
        }
        
        // NaverPlace 카테고리를 StoreCategory로 변환
        StoreCategory storeCategory = convertToStoreCategory(naverPlace.getCategory());
        
        // Store 생성
        Store store = storeService.create(
                owner,
                naverPlace.getName(),
                region,
                naverPlace.getAddress(),
                null, // addressDetail
                storeCategory,
                naverPlace.getCategory() + " 맛집", // description
                naverPlace.getContact(),
                "상세 주소를 입력해주세요", // direction
                false, // isNewOpen
                true,  // isTakeOut
                null,  // latitude
                null,  // longitude
                List.of() // subwayStations
        );
        
        // NaverPlace를 업로드 완료 상태로 변경
        naverPlace.markAsUploaded();
        naverPlaceRepository.save(naverPlace);
        
        return store.getId();
    }
    
    /**
     * NaverPlace 카테고리를 StoreCategory로 변환
     */
    private StoreCategory convertToStoreCategory(String naverCategory) {
        if (naverCategory == null) {
            return StoreCategory.ETC;
        }
        
        String lowerCategory = naverCategory.toLowerCase();
        
        if (lowerCategory.contains("한식") || lowerCategory.contains("한정식")) {
            return StoreCategory.KOREAN;
        } else if (lowerCategory.contains("중식") || lowerCategory.contains("중국")) {
            return StoreCategory.CHINESE;
        } else if (lowerCategory.contains("일식") || lowerCategory.contains("일본")) {
            return StoreCategory.JAPANESE;
        } else if (lowerCategory.contains("양식") || lowerCategory.contains("서양")) {
            return StoreCategory.WESTERN;
        } else if (lowerCategory.contains("카페") || lowerCategory.contains("디저트")) {
            return StoreCategory.CAFE;
        } else if (lowerCategory.contains("치킨") || lowerCategory.contains("닭")) {
            return StoreCategory.CHICKEN;
        } else if (lowerCategory.contains("피자")) {
            return StoreCategory.PIZZA;
        } else if (lowerCategory.contains("햄버거")) {
            return StoreCategory.BURGER;
        } else if (lowerCategory.contains("분식")) {
            return StoreCategory.SNACK;
        } else if (lowerCategory.contains("술") || lowerCategory.contains("바")) {
            return StoreCategory.BAR;
        } else {
            return StoreCategory.ETC;
        }
    }
}
