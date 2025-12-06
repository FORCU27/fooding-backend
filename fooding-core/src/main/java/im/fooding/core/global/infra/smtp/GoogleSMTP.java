package im.fooding.core.global.infra.smtp;


import jakarta.mail.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
@Slf4j
@RequiredArgsConstructor
public class GoogleSMTP {
    @Value("${email.smtp.host}")
    private String SMTP_HOST;
    @Value("${email.smtp.port}")
    private String SMTP_PORT;
    @Value("${email.username}")
    private String USERNAME;
    @Value("${email.password}")
    private String PASSWORD;

    private final GoogleSMTPTemplate  smtpTemplate;

    public boolean sendEmail( String targetEmail, String url, String name, String expiredAt ){
        try{
            Properties props = new Properties();
            props.put( "mail.smtp.host", SMTP_HOST );
            props.put( "mail.smtp.port", SMTP_PORT );
            props.put( "mail.smtp.auth", "true" );
            props.put( "mail.smtp.starttls.enable", "true" );
            props.put( "mail.smtp.ssl.trust", SMTP_HOST );

            Authenticator auth = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication( USERNAME, PASSWORD );
                }
            };
            Session session = Session.getInstance( props, auth );
            Message message = smtpTemplate.createMessage( session, USERNAME, targetEmail, "[Fooding] 비밀번호 재설정", url, name, expiredAt );
            Transport.send( message );
            return true;
        }
        catch( Exception e ){
            System.out.println( e.getMessage() );
        }
        return false;
    }
}
