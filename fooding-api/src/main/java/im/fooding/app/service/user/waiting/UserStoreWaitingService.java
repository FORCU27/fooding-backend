package im.fooding.app.service.user.waiting;

import im.fooding.app.dto.request.user.waiting.UserStoreWaitingRegisterRequest;
import im.fooding.app.dto.response.user.waiting.UserStoreWaitingCreateResponse;
import im.fooding.app.dto.response.user.waiting.UserStoreWaitingResponse;
import im.fooding.app.service.user.notification.UserNotificationApplicationService;
import im.fooding.core.dto.request.waiting.StoreWaitingRegisterRequest;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.StoreService;
import im.fooding.core.model.store.StoreServiceType;
import im.fooding.core.model.user.User;
import im.fooding.core.model.waiting.StoreWaiting;
import im.fooding.core.model.waiting.StoreWaitingChannel;
import im.fooding.core.model.waiting.WaitingSetting;
import im.fooding.core.repository.store.StoreServiceFilter;
import im.fooding.core.service.plan.PlanService;
import im.fooding.core.service.store.StoreServiceService;
import im.fooding.core.service.user.UserService;
import im.fooding.core.service.waiting.StoreWaitingService;
import im.fooding.core.service.waiting.WaitingLogService;
import im.fooding.core.service.waiting.WaitingSettingService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserStoreWaitingService {

    private final StoreWaitingService storeWaitingService;
    private final StoreServiceService storeServiceService;
    private final WaitingLogService waitingLogService;
    private final UserService userService;
    private final PlanService planService;
    private final UserNotificationApplicationService userNotificationApplicationService;
    private final WaitingSettingService waitingSettingService;

    public UserStoreWaitingResponse getStoreWaiting(long id) {
        return UserStoreWaitingResponse.from(storeWaitingService.get(id));
    }

    @Transactional
    public UserStoreWaitingCreateResponse registerStoreWaiting(UserStoreWaitingRegisterRequest request, long userId) {
        StoreServiceFilter filter = StoreServiceFilter.builder()
                .storeId(request.getStoreId())
                .type(StoreServiceType.WAITING)
                .build();
        StoreService storeService = storeServiceService.get(filter);
        WaitingSetting waitingSetting = waitingSettingService.getByStoreService(storeService);
        storeWaitingService.validate(waitingSetting);

        User user = userService.findById(userId);

        StoreWaitingRegisterRequest storeWaitingRegisterRequest = StoreWaitingRegisterRequest.builder()
                .user(user)
                .waitingUser(null)
                .store(waitingSetting.getStoreService().getStore())
                .channel(StoreWaitingChannel.IN_PERSON.getValue())
                .infantChairCount(request.getInfantChairCount())
                .infantCount(request.getInfantCount())
                .adultCount(request.getAdultCount())
                .build();

        StoreWaiting storeWaiting = storeWaitingService.register(storeWaitingRegisterRequest);

        waitingLogService.logRegister(storeWaiting);

        ObjectId planId = planService.create(storeWaiting);

        if (StringUtils.hasText(user.getPhoneNumber())) {
            sendSmsNotification(waitingSetting, storeWaiting, user.getPhoneNumber());
        }
        if (StringUtils.hasText(user.getEmail())) {
            sendEmailNotification(waitingSetting, storeWaiting, user.getEmail());
        }

        return new UserStoreWaitingCreateResponse(storeWaiting.getId(), planId.toString());
    }

    private void sendSmsNotification(WaitingSetting waitingSetting, StoreWaiting storeWaiting, String phoneNumber) {
        int order = storeWaitingService.getOrder(storeWaiting.getId());
        int personnel = storeWaiting.getAdultCount() + storeWaiting.getInfantCount();

        Store store = waitingSetting.getStoreService().getStore();
        userNotificationApplicationService.sendSmsWaitingRegisterMessage(
                store.getName(),
                personnel,
                order,
                storeWaiting.getCallNumber(),
                phoneNumber
        );
    }

    private void sendEmailNotification(WaitingSetting waitingSetting, StoreWaiting storeWaiting, String email) {
        int order = storeWaitingService.getOrder(storeWaiting.getId());
        int personnel = storeWaiting.getAdultCount() + storeWaiting.getInfantCount();

        Store store = waitingSetting.getStoreService().getStore();
        userNotificationApplicationService.sendSmsWaitingRegisterMessage(
                store.getName(),
                personnel,
                order,
                storeWaiting.getCallNumber(),
                email
        );
    }
}
