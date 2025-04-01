package im.fooding.app.service.notification;

import im.fooding.core.global.infra.slack.SlackClient;
import im.fooding.core.global.util.WaitingMessageBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationApplicationService {
    private final SlackClient slackClient;

    public void sendWaitingMessage(String store, String notice, int personnel, int myOrder, int waitingNumber, int delayWaitingNumber) {
        String message = WaitingMessageBuilder.buildWaitingMessage(store, notice, personnel, myOrder, waitingNumber, delayWaitingNumber);
        slackClient.sendNotificationMessage(message);
    }

    public void sendEnterStoreMessage(String store, String notice, int waitingNumber, int limitTime) {
        String message = WaitingMessageBuilder.buildEnterStoreMessage(store, notice, waitingNumber, limitTime);
        slackClient.sendNotificationMessage(message);
    }
}
