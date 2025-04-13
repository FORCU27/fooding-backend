package im.fooding.core.service.waiting;

import im.fooding.core.dto.request.waiting.StoreWaitingRegisterRequest;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.waiting.StoreWaiting;
import im.fooding.core.model.waiting.StoreWaitingChannel;
import im.fooding.core.model.waiting.Waiting;
import im.fooding.core.model.waiting.WaitingStatus;
import im.fooding.core.repository.waiting.StoreWaitingRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class StoreWaitingService {

    private final StoreWaitingRepository storeWaitingRepository;

    public StoreWaiting getStoreWaiting(long id) {
        return storeWaitingRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.STORE_WAITING_NOT_FOUND));
    }

    @Transactional
    public StoreWaiting register(StoreWaitingRegisterRequest request) {
        // TODO: 추후에 redis 로 개선
        int callNumber = (int) storeWaitingRepository.countCreatedOn(LocalDate.now()) + 1;

        StoreWaiting storeWaiting = StoreWaiting.builder()
                .user(request.user())
                .store(request.store())
                .callNumber(callNumber)
                .channel(StoreWaitingChannel.of(request.channel()))
                .infantChairCount(request.infantChairCount())
                .infantCount(request.infantCount())
                .adultCount(request.adultCount())
                .build();

        return storeWaitingRepository.save(storeWaiting);
    }

    public void validate(Waiting waiting) {
        if (waiting.getStatus() != WaitingStatus.WAITING_OPEN) {
            throw new ApiException(ErrorCode.WAITING_NOT_OPENED);
        }
    }
}
