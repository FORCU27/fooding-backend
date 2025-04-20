package im.fooding.app.service.user.notification;

import im.fooding.core.global.infra.slack.SlackClient;
import im.fooding.core.global.util.WaitingMessageBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserNotificationApplicationService {
    private final SlackClient slackClient;

    @Value("${message.sender}")
    private String SENDER;

    public void sendWaitingRegisterMessage(String storeName, int personnel, int order, int callNumber) {
        String message = WaitingMessageBuilder.buildRegisterMessage(
                storeName,
                personnel,
                order,
                callNumber
        );
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
        String message = WaitingMessageBuilder.buildEnterStoreMessage(SENDER, receiver, store, notice, waitingNumber, limitTime);
        slackClient.sendNotificationMessage(message);
    }
}
