package im.fooding.app.service.user.notification;

import im.fooding.app.dto.response.user.notification.UserNotificationResponse;
import im.fooding.core.global.infra.slack.SlackClient;
import im.fooding.core.global.util.WaitingMessageBuilder;
import im.fooding.core.model.notification.UserNotification;
import im.fooding.core.service.notification.UserNotificationService;
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

    @Value("${message.sender}")
    private String SENDER;

    public void sendWaitingRegisterMessage(String storeName, int personnel, int order, int callNumber) {
        String message = WaitingMessageBuilder.buildRegisterMessage(
                storeName,
                personnel,
                order,
                callNumber);
        slackClient.sendNotificationMessage(message);
    }

    public void sendWaitingCallMessage(String store, int callNumber, int entryTimeLimit) {
        String message = WaitingMessageBuilder.buildWaitingCallMessage(store, callNumber, entryTimeLimit);
        slackClient.sendNotificationMessage(message);
    }

    /**
     * 입장 메세지 발송(슬랙으로 대체) 예시 추후 삭제 요망
     *
     * @param receiver
     * @param store
     * @param notice
     * @param waitingNumber
     * @param limitTime
     */
    public void sendEnterStoreMessage(String receiver, String store, String notice, int waitingNumber, int limitTime) {
        String message = WaitingMessageBuilder.buildEnterStoreMessage(SENDER, receiver, store, notice, waitingNumber,
                limitTime);
        slackClient.sendNotificationMessage(message);
    }

    public void sendWaitingCancelMessage(String store, String reason) {
        String message = WaitingMessageBuilder.buildCancel(store, reason);
        slackClient.sendNotificationMessage(message);
    }

    public PageResponse<UserNotificationResponse> list(Long userId, Pageable pageable) {
        Page<UserNotification> page = userNotificationService.getUserNotifications(userId, pageable);
        return PageResponse.of(
                page.getContent().stream().map(UserNotificationResponse::from).toList(),
                PageInfo.of(page));
    }

    public UserNotificationResponse retrieve(Long userId, Long notificationId) {
        return UserNotificationResponse.from(userNotificationService.getNotification(userId, notificationId));
    }
}
