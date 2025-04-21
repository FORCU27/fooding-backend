package im.fooding.core.service.store;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.store.StoreImage;
import im.fooding.core.repository.store.StoreImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class StoreImageService {

    private final StoreImageRepository storeImageRepository;

    /**
     * 가게 대표 이미지 조회
     *
     * @param storeId
     * @return List<StoreImage>
     */
    public StoreImage get(long storeId) {
        return storeImageRepository.get(storeId)
                .orElseThrow(()-> new ApiException(ErrorCode.STORE_IMAGE_NOT_FOUND));
    }
}
