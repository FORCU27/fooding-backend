package im.fooding.app.service.user.waiting;

import im.fooding.app.dto.request.user.waiting.UserStoreWaitingRegisterRequest;
import im.fooding.app.dto.response.user.waiting.UserStoreWaitingCreateResponse;
import im.fooding.app.dto.response.user.waiting.UserStoreWaitingResponse;
import im.fooding.app.service.user.notification.UserNotificationApplicationService;
import im.fooding.core.dto.request.waiting.StoreWaitingRegisterRequest;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.user.User;
import im.fooding.core.model.waiting.StoreWaiting;
import im.fooding.core.model.waiting.StoreWaitingChannel;
import im.fooding.core.model.waiting.Waiting;
import im.fooding.core.service.plan.PlanService;
import im.fooding.core.service.store.StoreService;
import im.fooding.core.service.user.UserService;
import im.fooding.core.service.waiting.StoreWaitingService;
import im.fooding.core.service.waiting.WaitingLogService;
import im.fooding.core.service.waiting.WaitingService;
import java.util.Optional;
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
    private final StoreService storeService;
    private final WaitingService waitingService;
    private final WaitingLogService waitingLogService;
    private final UserService userService;
    private final PlanService planService;
    private final UserNotificationApplicationService userNotificationApplicationService;

    public UserStoreWaitingResponse getStoreWaiting(long id) {
        return UserStoreWaitingResponse.from(storeWaitingService.get(id));
    }

    @Transactional
    public UserStoreWaitingCreateResponse registerStoreWaiting(UserStoreWaitingRegisterRequest request, long userId) {
        Store store = storeService.findById(request.getStoreId());
        Waiting waiting = waitingService.getByStore(store);
        storeWaitingService.validate(waiting);

        User user = userService.findById(userId);

        StoreWaitingRegisterRequest storeWaitingRegisterRequest = StoreWaitingRegisterRequest.builder()
                .user(user)
                .waitingUser(null)
                .store(waiting.getStore())
                .channel(StoreWaitingChannel.IN_PERSON.getValue())
                .infantChairCount(request.getInfantChairCount())
                .infantCount(request.getInfantCount())
                .adultCount(request.getAdultCount())
                .build();

        StoreWaiting storeWaiting = storeWaitingService.register(storeWaitingRegisterRequest);

        waitingLogService.logRegister(storeWaiting);

        ObjectId planId = planService.create(storeWaiting);

        if (StringUtils.hasText(user.getPhoneNumber())) {
            sendSmsNotification(waiting, storeWaiting, user.getPhoneNumber());
        }
        if (StringUtils.hasText(user.getEmail())) {
            sendEmailNotification(waiting, storeWaiting, user.getEmail());
        }

        return new UserStoreWaitingCreateResponse(storeWaiting.getId(), planId.toString());
    }

    private void sendSmsNotification(Waiting waiting, StoreWaiting storeWaiting, String phoneNumber) {
        int order = storeWaitingService.getOrder(storeWaiting.getId());
        int personnel = storeWaiting.getAdultCount() + storeWaiting.getInfantCount();

        Store store = waiting.getStore();
        userNotificationApplicationService.sendSmsWaitingRegisterMessage(
                store.getName(),
                personnel,
                order,
                storeWaiting.getCallNumber(),
                phoneNumber
        );
    }

    private void sendEmailNotification(Waiting waiting, StoreWaiting storeWaiting, String email) {
        int order = storeWaitingService.getOrder(storeWaiting.getId());
        int personnel = storeWaiting.getAdultCount() + storeWaiting.getInfantCount();

        Store store = waiting.getStore();
        userNotificationApplicationService.sendSmsWaitingRegisterMessage(
                store.getName(),
                personnel,
                order,
                storeWaiting.getCallNumber(),
                email
        );
    }
}
