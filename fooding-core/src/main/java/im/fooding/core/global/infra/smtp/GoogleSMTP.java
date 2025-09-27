package im.fooding.core.global.infra.smtp;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GoogleSMTP {
    @Value("${email.smtp.host}")
    private String SMTP_HOST;
    @Value("${email.smtp.port}")
    private String SMTP_PORT;
    @Value("${email.username}")
    private String USERNAME;
    @Value("${email.password}")
    private String PASSWORD;

    public static boolean sendEmail( String targetEmail, GoogleSMTPTemplate template ){

        return true;
    }
}
