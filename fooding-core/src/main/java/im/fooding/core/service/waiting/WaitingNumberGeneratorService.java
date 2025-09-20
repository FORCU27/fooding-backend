package im.fooding.core.service.waiting;

import im.fooding.core.event.store.StoreWaitingServiceCreatedEvent;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.global.kafka.KafkaEventHandler;
import im.fooding.core.model.waiting.WaitingNumberGenerator;
import im.fooding.core.repository.waiting.WaitingNumberGeneratorRepository;
import im.fooding.core.service.store.StoreServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WaitingNumberGeneratorService {

    private final WaitingNumberGeneratorRepository waitingNumberGeneratorRepository;
    private final StoreServiceService storeServiceService;

    @Transactional
    public int issueNumber(long storeId) {
        WaitingNumberGenerator waitingNumberGenerator = getByStoreId(storeId);
        int issuedCallNumber = waitingNumberGenerator.issueCallNumber();

        waitingNumberGeneratorRepository.save(waitingNumberGenerator);

        return issuedCallNumber;
    }

    @Transactional
    public void resetNumber(long storeId) {
        WaitingNumberGenerator waitingNumberGenerator = getByStoreId(storeId);

        waitingNumberGenerator.resetCallNumber();

        waitingNumberGeneratorRepository.save(waitingNumberGenerator);
    }

    private WaitingNumberGenerator getByStoreId(long storeId) {
        return waitingNumberGeneratorRepository.findByStoreId(storeId)
                .filter(it -> !it.isDeleted())
                .orElseThrow(() -> new ApiException(ErrorCode.WAITING_NUMBER_GENERATOR_NOT_FOUND));
    }

    @Transactional
    @KafkaEventHandler(StoreWaitingServiceCreatedEvent.class)
    public void handleStoreWaitingServiceCreatedEvent(StoreWaitingServiceCreatedEvent event) {
        try {
            long storeId = storeServiceService.findById(event.id()).getStore().getId();
            WaitingNumberGenerator waitingNumberGenerator = new WaitingNumberGenerator(storeId);
            waitingNumberGeneratorRepository.save(waitingNumberGenerator);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
