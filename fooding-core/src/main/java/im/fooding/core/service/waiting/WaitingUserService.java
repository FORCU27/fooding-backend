package im.fooding.core.service.waiting;

import im.fooding.core.dto.request.waiting.WaitingUserRegisterRequest;
import im.fooding.core.model.waiting.WaitingUser;
import im.fooding.core.repository.waiting.WaitingUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class WaitingUserService {

    private final WaitingUserRepository waitingUserRepository;

    @Transactional
    public WaitingUser getOrElseRegister(WaitingUserRegisterRequest request) {
        return waitingUserRepository.findByStoreAndPhoneNumber(request.store(), request.phoneNumber())
                .filter(it -> !it.isDeleted())
                .orElseGet(() -> register(request));
    }

    @Transactional
    public WaitingUser register(WaitingUserRegisterRequest request) {
        return waitingUserRepository.saveAndFlush(request.toWaitingUser());
    }
}
