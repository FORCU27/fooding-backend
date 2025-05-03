package im.fooding.core.service.waiting;

import im.fooding.core.dto.request.waiting.WaitingUserCreateRequest;
import im.fooding.core.dto.request.waiting.WaitingUserRegisterRequest;
import im.fooding.core.dto.request.waiting.WaitingUserUpdateRequest;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.Waiting;
import im.fooding.core.model.waiting.WaitingUser;
import im.fooding.core.repository.waiting.WaitingUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        WaitingUser waitingUser = request.toWaitingUser();
        validPolicyAgreed(waitingUser);
        return waitingUserRepository.save(waitingUser);
    }

    @Transactional
    public WaitingUser create(WaitingUserCreateRequest request) {
        WaitingUser waitingUser = request.toWaitingUser();
        validPolicyAgreed(waitingUser);
        return waitingUserRepository.save(waitingUser);
    }

    private void validPolicyAgreed(WaitingUser waitingUser) {
        if (!waitingUser.isPrivacyPolicyAgreed()) {
            throw new ApiException(ErrorCode.WAITING_USER_PRIVACY_POLICY_AGREED_REQUIRED);
        }
        if (!waitingUser.isTermsAgreed()) {
            throw new ApiException(ErrorCode.WAITING_USER_TERMS_AGREED_AGREED_REQUIRED);
        }
        if (!waitingUser.isThirdPartyAgreed()) {
            throw new ApiException(ErrorCode.WAITING_USER_THIRD_PARTY_AGREED_REQUIRED);
        }
    }

    public WaitingUser get(long id) {
        return waitingUserRepository.findById(id)
                .filter(it -> !it.isDeleted())
                .orElseThrow(() -> new ApiException(ErrorCode.WAITING_USER_NOT_FOUND));
    }

    public Page<WaitingUser> getList(Pageable pageable) {
        return waitingUserRepository.findAllByDeletedFalse(pageable);
    }

    public WaitingUser update(WaitingUserUpdateRequest request) {
        WaitingUser waitingUser = get(request.id());
        waitingUser.update(
                request.store(),
                request.name(),
                request.phoneNumber(),
                request.termsAgreed(),
                request.privacyPolicyAgreed(),
                request.thirdPartyAgreed(),
                request.marketingConsent(),
                request.count()
        );
        return waitingUser;
    }

    @Transactional
    public void delete(long id, long deletedBy) {
        WaitingUser waiting = get(id);
        waiting.delete(deletedBy);
    }
}
