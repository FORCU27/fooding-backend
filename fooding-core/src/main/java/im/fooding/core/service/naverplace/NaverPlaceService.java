package im.fooding.core.service.naverplace;

import im.fooding.core.common.PageResponse;
import im.fooding.core.model.naverplace.NaverPlace;
import im.fooding.core.repository.naverplace.NaverPlaceRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NaverPlaceService {

    private final NaverPlaceRepository naverPlaceRepository;

    @Transactional
    public String create(
            String name,
            String category,
            String address,
            String contact,
            List<NaverPlace.Menu> menus
    ) {
        NaverPlace naverPlace = NaverPlace.builder()
                .name(name)
                .category(category)
                .address(address)
                .contact(contact)
                .menus(menus)
                .build();

        return naverPlaceRepository.save(naverPlace)
                .getId();
    }

    public Page<NaverPlace> getNaverPlaces(Pageable pageable) {
        return naverPlaceRepository.findAll(pageable);
    }
}
