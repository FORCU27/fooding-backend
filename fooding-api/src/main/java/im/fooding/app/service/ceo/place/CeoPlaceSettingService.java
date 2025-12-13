package im.fooding.app.service.ceo.place;

import im.fooding.app.dto.request.ceo.place.CeoPlaceSettingCreateRequest;
import im.fooding.app.dto.request.ceo.place.CeoPlaceSettingUpdateRequest;
import im.fooding.app.dto.response.ceo.place.CeoPlaceSettingResponse;
import im.fooding.core.model.place.PlaceSetting;
import im.fooding.core.model.store.Store;
import im.fooding.core.service.place.PlaceSettingService;
import im.fooding.core.service.store.StoreMemberService;
import im.fooding.core.service.store.StoreService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CeoPlaceSettingService {

    private final PlaceSettingService placeSettingService;
    private final StoreService storeService;
    private final StoreMemberService storeMemberService;

    public Long create(CeoPlaceSettingCreateRequest request, long userId) {
        storeMemberService.checkMember(request.getStoreId(), userId);
        Store store = storeService.findById(request.getStoreId());
        PlaceSetting placeSetting = placeSettingService.create(
                store,
                request.getMetadata(),
                request.getHeaderTitle(),
                request.getBody().getEnabled(),
                request.getBody().getContent()
        );
        return placeSetting.getId();
    }

    @Transactional(readOnly = true)
    public List<CeoPlaceSettingResponse> list(Long storeId, long userId) {
        storeMemberService.checkMember(storeId, userId);
        return placeSettingService.list(storeId).stream()
                .map(CeoPlaceSettingResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public CeoPlaceSettingResponse retrieve(Long id, long userId) {
        PlaceSetting placeSetting = placeSettingService.get(id);
        storeMemberService.checkMember(placeSetting.getStore().getId(), userId);
        return CeoPlaceSettingResponse.from(placeSetting);
    }

    public void update(Long id, CeoPlaceSettingUpdateRequest request, long userId) {
        PlaceSetting placeSetting = placeSettingService.get(id);
        storeMemberService.checkMember(placeSetting.getStore().getId(), userId);
        placeSettingService.update(id, request.getMetadata(), request.getHeaderTitle(), request.getBody().getEnabled(), request.getBody().getContent());
    }

    public void delete(Long id, long userId) {
        PlaceSetting placeSetting = placeSettingService.get(id);
        storeMemberService.checkMember(placeSetting.getStore().getId(), userId);
        placeSettingService.delete(id, userId);
    }
}
