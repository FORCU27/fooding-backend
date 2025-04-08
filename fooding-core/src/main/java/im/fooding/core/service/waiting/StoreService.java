package im.fooding.core.service.waiting;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.store.Store;
import im.fooding.core.repository.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class StoreService {

    private final StoreRepository storeRepository;

    public Store getById(long id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.STORE_NOT_FOUND));
    }
}
