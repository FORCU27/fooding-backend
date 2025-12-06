package im.fooding.core.event.auth;

import im.fooding.core.global.infra.slack.SlackClient;
import im.fooding.core.global.infra.smtp.GoogleSMTP;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthFindEventListener {
    private final SlackClient slackClient;
    private final GoogleSMTP googleSMTP;

    @Value("${email.password-uri}")
    private String PASSWORD_RESET_WEB_BASE_URL;

    // 휴대폰 6자리 코드 인증
    @EventListener
    public void handlePhoneAuthenticateEvent( AuthPhoneAuthenticateEvent event ){
        String slackMessage = String.format(
                        "[WEB 발신]\n " +
                        "[푸딩],\n\n" +
                        "인증번호 %s를 입력하세요",
                event.code()
        );
        slackClient.sendNotificationMessage(slackMessage);
    }
    
    // 휴대폰으로 비밀번호 재설정 링크 전달
    @EventListener
    public void handleSendUrlByPhoneEvent( AuthGetResetUrlByPhoneEvent event ){
        String slackMessage = String.format(
                        "[WEB 발신]\n " +
                        "[푸딩 사장님 비밀번호 변경 안내] \n " +
                        "안녕하세요. %s님 계정 비밀번호 변경을 위한 URL을 전달드립니다. \n " +
                        "아래 URL을 통해 비밀번호 변경을 진행해주세요. \n\n" +
                        "%s",
                event.name(),
                PASSWORD_RESET_WEB_BASE_URL + "?encodedLine=" + event.resetUrl()
        );
        slackClient.sendNotificationMessage(slackMessage);
    }
    
    // 이메일로 비밀번호 재설정 링크 전달
    @EventListener
    public void handleSendUrlByEmailEvent( AuthGetResetUrlByEmailEvent event ){
        googleSMTP.sendEmail( event.email(), event.resetUrl(), event.name(), event.expiredAt() );
    }
}
