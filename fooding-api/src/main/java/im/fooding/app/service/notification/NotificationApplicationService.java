package im.fooding.app.service.notification;

import im.fooding.core.global.infra.slack.SlackClient;
import im.fooding.core.global.util.WaitingMessageBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationApplicationService {
    private final SlackClient slackClient;

    @Value("${message.sender}")
    private String SENDER;

    /**
     * 웨이팅 메세지 발송(슬랙으로 대체) 테스트
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
     * 입장 메세지 발송(슬랙으로 대체) 테스트
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
}
