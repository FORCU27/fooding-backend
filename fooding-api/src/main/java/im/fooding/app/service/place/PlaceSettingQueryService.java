package im.fooding.app.service.place;

import im.fooding.app.dto.response.place.PlaceSettingResponse;
import im.fooding.core.service.place.PlaceSettingService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlaceSettingQueryService {

    private final PlaceSettingService placeSettingService;

    public List<PlaceSettingResponse> list(Long storeId) {
        return placeSettingService.list(storeId).stream()
                .map(PlaceSettingResponse::from)
                .toList();
    }
}
