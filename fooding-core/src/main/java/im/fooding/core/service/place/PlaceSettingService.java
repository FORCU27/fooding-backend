package im.fooding.core.service.place;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.place.PlaceSetting;
import im.fooding.core.model.store.Store;
import im.fooding.core.repository.place.PlaceSettingRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlaceSettingService {

    private final PlaceSettingRepository placeSettingRepository;

    @Transactional
    public PlaceSetting create(Store store, String metadata, String headerTitle, boolean bodyEnabled, String bodyContent) {
        PlaceSetting placeSetting = PlaceSetting.create(store, metadata, headerTitle, bodyEnabled, bodyContent);
        return placeSettingRepository.save(placeSetting);
    }

    public List<PlaceSetting> list(Long storeId) {
        return placeSettingRepository.findByStoreIdAndDeletedFalse(storeId);
    }

    public PlaceSetting get(Long id) {
        return placeSettingRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ApiException(ErrorCode.PLACE_SETTING_NOT_FOUND));
    }

    @Transactional
    public PlaceSetting update(Long id, String metadata, String headerTitle, boolean bodyEnabled, String bodyContent) {
        PlaceSetting placeSetting = get(id);
        placeSetting.update(metadata, headerTitle, bodyEnabled, bodyContent);
        return placeSetting;
    }

    @Transactional
    public void delete(Long id, long deletedBy) {
        PlaceSetting placeSetting = get(id);
        placeSetting.delete(deletedBy);
    }
}
