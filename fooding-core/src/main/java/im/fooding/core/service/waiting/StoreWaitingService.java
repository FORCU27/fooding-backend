package im.fooding.core.service.waiting;

import im.fooding.core.dto.request.waiting.StoreWaitingRegisterRequest;
import im.fooding.core.model.waiting.StoreWaiting;
import im.fooding.core.model.waiting.StoreWaitingChannel;
import im.fooding.core.repository.waiting.StoreWaitingRepository;
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

    @Transactional
    public StoreWaiting register(StoreWaitingRegisterRequest request) {
        int callNumber = (int) storeWaitingRepository.countTodayCreated() + 1;

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
}
