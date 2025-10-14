package im.fooding.app.service.user.notification;

import im.fooding.app.dto.response.user.notification.UserNotificationResponse;
import im.fooding.core.event.reward.RewardEarnEvent;
import im.fooding.core.event.reward.RewardUseEvent;
import im.fooding.core.event.waiting.StoreWaitingCallEvent;
import im.fooding.core.event.waiting.StoreWaitingCanceledEvent;
import im.fooding.core.event.waiting.StoreWaitingRegisteredEvent;
import im.fooding.core.global.infra.slack.SlackClient;
import im.fooding.core.global.kafka.KafkaEventHandler;
import im.fooding.core.model.notification.NotificationTemplate;
import im.fooding.core.model.notification.NotificationTemplate.Type;
import im.fooding.core.model.notification.UserNotification;
import im.fooding.core.model.reward.RewardPoint;
import im.fooding.core.model.waiting.StoreWaiting;
import im.fooding.core.model.waiting.WaitingSetting;
import im.fooding.core.model.waiting.WaitingUser;
import im.fooding.core.service.notification.NotificationTemplateService;
import im.fooding.core.service.notification.UserNotificationService;
import im.fooding.core.service.reward.RewardService;
import im.fooding.core.service.waiting.StoreWaitingService;
import im.fooding.core.service.waiting.WaitingSettingService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserNotificationApplicationService {
    private final SlackClient slackClient;
    private final UserNotificationService userNotificationService;
    private final NotificationTemplateService notificationTemplateService;
    private final StoreWaitingService storeWaitingService;
    private final WaitingSettingService waitingSettingService;
    private final RewardService rewardService;

    @Value("${message.sender}")
    private String SENDER;

    public void sendEmailWaitingRegisterMessage(String storeName, int personnel, int order, int callNumber, String email) {
        NotificationTemplate template = notificationTemplateService.getByType(NotificationTemplate.Type.WaitingCreatedEmail);
        String subject = template.getSubject();
        String content = template.getContent().formatted(
                storeName,
                personnel,
                order,
                callNumber
        );
        String message = buildMessage(subject, content);
        slackClient.sendNotificationMessage(message);
    }

    public void sendSmsWaitingRegisterMessage(String storeName, int personnel, int order, int callNumber, String phoneNumber) {
        NotificationTemplate template = notificationTemplateService.getByType(NotificationTemplate.Type.WaitingCreatedSms);
        String subject = template.getSubject();
        String content = template.getContent().formatted(
                storeName,
                personnel,
                order,
                callNumber
        );
        String message = buildMessage(subject, content);
        slackClient.sendNotificationMessage(message);
    }

    @KafkaEventHandler(StoreWaitingRegisteredEvent.class)
    public void sendWaitingRegisterMessage(StoreWaitingRegisteredEvent event) {
        StoreWaiting storeWaiting = storeWaitingService.get(event.storeWaitingId());

        String storeName = storeWaiting.getStoreName();
        int personnel = storeWaiting.getAdultCount() + storeWaiting.getInfantCount();
        int order = storeWaitingService.getOrder(storeWaiting.getId());
        int callNumber = storeWaiting.getCallNumber();

        // WaitingUser 처리
        Optional.ofNullable(storeWaiting.getWaitingUser())
                .map(WaitingUser::getPhoneNumber)
                .ifPresent(phoneNumber -> sendSmsWaitingRegisterMessage(
                        storeName, personnel, order, callNumber, phoneNumber
                ));

        // User 처리
        Optional.ofNullable(storeWaiting.getUser())
                .ifPresent(user -> {
                    Optional.ofNullable(user.getEmail())
                            .ifPresent(email -> sendEmailWaitingRegisterMessage(
                                    storeName, personnel, order, callNumber, email
                            ));
                    Optional.ofNullable(user.getPhoneNumber())
                            .ifPresent(phoneNumber -> sendSmsWaitingRegisterMessage(
                                    storeName, personnel, order, callNumber, phoneNumber
                            ));
                });
    }

    @KafkaEventHandler(StoreWaitingCallEvent.class)
    public void sendWaitingCallMessage(StoreWaitingCallEvent event) {
        StoreWaiting storeWaiting = storeWaitingService.get(event.storeWaitingId());
        WaitingSetting waitingSetting = waitingSettingService.getActiveSetting(storeWaiting.getStore());

        NotificationTemplate template = notificationTemplateService.getByType(Type.WaitingCallSms);

        String subject = template.getSubject();
        String content = template.getContent().formatted(
                storeWaiting.getCallNumber(),
                storeWaiting.getStoreName(),
                waitingSetting.getEntryTimeLimitMinutes()
        );

        String message = buildMessage(subject, content);

        slackClient.sendNotificationMessage(message);
    }

    @KafkaEventHandler(StoreWaitingCanceledEvent.class)
    public void sendWaitingCancelMessage(StoreWaitingCanceledEvent event) {
        NotificationTemplate template = notificationTemplateService.getByType(Type.WaitingCancelSms);

        String storeName = storeWaitingService.get(event.storeWaitingId())
                .getStoreName();

        String subject = template.getSubject();
        String content = template.getContent().formatted(
                storeName,
                event.reason()
        );
        String message = buildMessage(subject, content);
        slackClient.sendNotificationMessage(message);
    }

    public PageResponse<UserNotificationResponse> list(Long userId, Pageable pageable) {
        Page<UserNotification> page = userNotificationService.getUserNotifications(userId, pageable);
        return PageResponse.of(
                page.getContent().stream().map(UserNotificationResponse::from).toList(),
                PageInfo.of(page));
    }

    @Transactional
    public void markAsRead(Long userId) {
        List<UserNotification> notifications = userNotificationService.getUnreadUserNotifications(userId);
        notifications.forEach(UserNotification::read);
    }

    public UserNotificationResponse retrieve(Long userId, Long notificationId) {
        return UserNotificationResponse.from(userNotificationService.getNotification(userId, notificationId));
    }

    @KafkaEventHandler(RewardEarnEvent.class)
    public void sendRewardEarnMessage(RewardEarnEvent event) {
        NotificationTemplate template = notificationTemplateService.getByType(Type.RewardEarnSms);

        String subject = template.getSubject();
        String content = template.getContent().formatted(
                event.storeName(),
                event.point()
        );
        String message = buildMessage(subject, content);
        slackClient.sendNotificationMessage(message);
    }

    @KafkaEventHandler(RewardUseEvent.class)
    public void sendRewardUseMessage(RewardUseEvent event) {
        NotificationTemplate template = notificationTemplateService.getByType(Type.RewardUseSms);

        RewardPoint rewardPoint = rewardService.get(event.rewardId());
        String storeName = rewardPoint.getStore().getName();

        String subject = template.getSubject();
        String content = template.getContent().formatted(
                storeName,
                event.usePoint(),
                event.remainPoint()
        );

        String message = buildMessage(subject, content);
        slackClient.sendNotificationMessage(message);
    }

    private String buildMessage(String subject, String content) {
        return """
                title
                %s
                body
                %s
                """.formatted(subject, content);
    }
}
