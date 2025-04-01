package im.fooding.core.global.infra.slack;

import com.slack.api.Slack;
import com.slack.api.model.Attachment;
import com.slack.api.model.Field;
import im.fooding.core.global.exception.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.slack.api.webhook.WebhookPayloads.payload;

@Service
@Slf4j
public class SlackClient {
    @Value("${webhook.slack.url}")
    private String SLACK_WEBHOOK_URL;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    private final Slack slackClient = Slack.getInstance();


    /**
     * Slack 에러메시지 전송
     *
     * @param errorResponse
     */
    @Async
    public void sendErrorMessage(ErrorResponse errorResponse, StackTraceElement[] getStackTrace) {
        try {
            String profile = activeProfile.equals("prod") ? "*[PROD] " : "*[STAGING] ";
            slackClient.send(SLACK_WEBHOOK_URL, payload(p -> p
                    .text(profile + SlackConstant.ERROR.TITLE)
                    .attachments(List.of(Attachment.builder().color(SlackConstant.ERROR.COLOR)
                            .fields(List.of(
                                    generateSlackField(SlackConstant.ERROR.STATUS, String.valueOf(errorResponse.getStatus())),
                                    generateSlackField(SlackConstant.ERROR.TIME, errorResponse.getTimestamp().toString()),
                                    generateSlackField(SlackConstant.ERROR.CODE, errorResponse.getCode()),
                                    generateSlackField(SlackConstant.ERROR.MESSAGE, errorResponse.getMessage()),
                                    generateSlackField(SlackConstant.ERROR.STACK_TRACE, Arrays.toString(getStackTrace))
                            ))
                            .build())))
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Slack 안내 메세지 전송
     *
     * @param message
     */
    @Async
    public void sendNotificationMessage(String message) {
        try {
            String profile = activeProfile.equals("prod") ? "*[PROD] " : "*[STAGING] ";
            slackClient.send(SLACK_WEBHOOK_URL, payload(p -> p
                    .text(profile + SlackConstant.NOTIFICATION.TITLE)
                    .attachments(List.of(Attachment.builder().color(SlackConstant.NOTIFICATION.COLOR)
                            .fields(List.of(
                                    generateSlackField(SlackConstant.NOTIFICATION.TIME, LocalDateTime.now().toString()),
                                    generateSlackField(SlackConstant.NOTIFICATION.MESSAGE, message)
                            ))
                            .build())))
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Slack Field 생성
     *
     * @param title
     * @param value
     * @return Field
     */
    private Field generateSlackField(String title, String value) {
        return Field.builder()
                .title(title)
                .value(value)
                .valueShortEnough(false)
                .build();
    }
}
