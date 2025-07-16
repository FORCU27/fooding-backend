package im.fooding.core.event.coupon;

import im.fooding.core.global.infra.slack.SlackClient;
import im.fooding.core.model.notification.NotificationChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RequestCouponListener {
    private final SlackClient slackClient;

    @EventListener
    public void handleNotificationCouponEvent(RequestCouponEvent event) {
        String slackMessage = String.format(
                "[푸딩 쿠폰 사용 안내]\n \"%s\"을 사용하셨습니다. \uD83C\uDF9F\uFE0F \n가게 직원에게 이 메세지를 보여주세요!\n\n---\n\n발송 정보\n - 채널: %s\n - 번호: %s",
                event.name(),
                event.channel(),
                event.receiverNumber()
        );
        slackClient.sendNotificationMessage(slackMessage);
    }
}
