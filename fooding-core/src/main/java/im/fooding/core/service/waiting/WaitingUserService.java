package im.fooding.core.service.waiting;

import im.fooding.core.dto.request.waiting.WaitingUserCreateRequest;
import im.fooding.core.dto.request.waiting.WaitingUserRegisterRequest;
import im.fooding.core.dto.request.waiting.WaitingUserUpdateRequest;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.waiting.WaitingUser;
import im.fooding.core.model.waiting.WaitingUserPolicyAgreement;
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
    public void create(WaitingUserCreateRequest request) {
        WaitingUser waitingUser = request.toWaitingUser();
        validPolicyAgreed(waitingUser.getPolicyAgreement());
        waitingUserRepository.save(waitingUser);
    }

    public WaitingUser get(long id) {
        return waitingUserRepository.findById(id)
                .filter(it -> !it.isDeleted())
                .orElseThrow(() -> new ApiException(ErrorCode.WAITING_USER_NOT_FOUND));
    }

    public Page<WaitingUser> list(Pageable pageable) {
        return waitingUserRepository.findAllByDeletedFalse(pageable);
    }

    @Transactional
    public void update(WaitingUserUpdateRequest request) {
        WaitingUser waitingUser = get(request.id());

        WaitingUserPolicyAgreement policyAgreement = WaitingUserPolicyAgreement.builder()
                .termsAgreed(request.termsAgreed())
                .privacyPolicyAgreed(request.privacyPolicyAgreed())
                .thirdPartyAgreed(request.thirdPartyAgreed())
                .build();

        validPolicyAgreed(policyAgreement);

        waitingUser.update(
                request.store(),
                request.name(),
                request.phoneNumber(),
                policyAgreement,
                request.count()
        );
    }

    @Transactional
    public void delete(long id, long deletedBy) {
        WaitingUser waiting = get(id);
        waiting.delete(deletedBy);
    }

    @Transactional
    public WaitingUser getOrElseRegister(WaitingUserRegisterRequest request) {
        return waitingUserRepository.findByStoreAndPhoneNumber(request.store(), request.phoneNumber())
                .filter(it -> !it.isDeleted())
                .orElseGet(() -> register(request));
    }

    @Transactional
    public WaitingUser register(WaitingUserRegisterRequest request) {
        WaitingUser waitingUser = request.toWaitingUser();

        validPolicyAgreed(waitingUser.getPolicyAgreement());
        return waitingUserRepository.save(waitingUser);
    }

    private void validPolicyAgreed(WaitingUserPolicyAgreement policyAgreement) {
        if (!policyAgreement.isPrivacyPolicyAgreed()) {
            throw new ApiException(ErrorCode.WAITING_USER_PRIVACY_POLICY_AGREED_REQUIRED);
        }
        if (!policyAgreement.isTermsAgreed()) {
            throw new ApiException(ErrorCode.WAITING_USER_TERMS_AGREED_AGREED_REQUIRED);
        }
        if (!policyAgreement.isThirdPartyAgreed()) {
            throw new ApiException(ErrorCode.WAITING_USER_THIRD_PARTY_AGREED_REQUIRED);
        }
    }
}
