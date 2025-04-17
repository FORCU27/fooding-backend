package im.fooding.app.service.user.notification;

import im.fooding.app.dto.response.user.notification.UserNotificationResponse;
import im.fooding.core.global.infra.slack.SlackClient;
import im.fooding.core.global.util.WaitingMessageBuilder;
import im.fooding.core.service.notification.UserNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserNotificationApplicationService {
    private final SlackClient slackClient;
    private final UserNotificationService userNotificationService;

    @Value("${message.sender}")
    private String SENDER;

    /**
     * 웨이팅 메세지 발송(슬랙으로 대체) 예시 추후 삭제 요망
     *
     * @param receiver
     * @param store
     * @param notice
     * @param personnel
     * @param myOrder
     * @param waitingNumber
     * @param delayWaitingNumber
     */
    public void sendWaitingMessage(String receiver, String store, String notice, int personnel, int myOrder, int waitingNumber, int delayWaitingNumber) {
        String message = WaitingMessageBuilder.buildWaitingMessage(SENDER, receiver, store, notice, personnel, myOrder, waitingNumber, delayWaitingNumber);
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
        String message = WaitingMessageBuilder.buildEnterStoreMessage(SENDER, receiver, store, notice, waitingNumber, limitTime);
        slackClient.sendNotificationMessage(message);
    }

    public List<UserNotificationResponse> list(Long userId) {
        return userNotificationService.getUserNotifications(userId).stream()
                .map(UserNotificationResponse::from)
                .toList();
    }

    public UserNotificationResponse retrieve(Long userId, Long notificationId) {
        return UserNotificationResponse.from(userNotificationService.getNotification(userId, notificationId));
    }
}
