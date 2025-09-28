package im.fooding.core.event.auth;

import im.fooding.core.global.infra.slack.SlackClient;
import im.fooding.core.global.infra.smtp.GoogleSMTP;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthFindEventListener {
    private final SlackClient slackClient;
    private final GoogleSMTP googleSMTP;

    // 휴대폰 6자리 코드 인증
    @EventListener
    public void handlePhoneAuthenticateEvent( AuthPhoneAuthenticateEvent event ){
        String slackMessage = String.format(
                "[본인 인증 안내]\n \"%s\"님, 본인인증 코드입니다. \n[ %s ]\n\n---\n\n발송 정보\n - 채널: %s\n - 번호: %s",
                event.name(),
                event.code(),
                event.channel(),
                event.phoneNumber()
        );
        slackClient.sendNotificationMessage(slackMessage);
    }
    
    // 휴대폰으로 비밀번호 재설정 링크 전달
    @EventListener
    public void handleSendUrlByPhoneEvent( AuthGetResetUrlByPhoneEvent event ){
        String slackMessage = String.format(
                "[비밀번호 재설정 안내]\n \"%s\"님, 비밀번호 재설정을 위한 주소입니다.. \n %s \n\n---\n\n발송 정보\n - 채널: %s\n - 번호: %s",
                event.name(),
                event.resetUrl(),
                event.channel(),
                event.phoneNumber()
        );
        slackClient.sendNotificationMessage(slackMessage);
    }
    
    // 이메일로 비밀번호 재설정 링크 전달
    @EventListener
    public void handleSendUrlByEmailEvent( AuthGetResetUrlByEmailEvent event ){
        googleSMTP.sendEmail( event.email(), event.resetUrl() );
    }
}
