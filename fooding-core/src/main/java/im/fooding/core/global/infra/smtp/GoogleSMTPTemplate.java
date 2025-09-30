package im.fooding.core.global.infra.smtp;

import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.stereotype.Component;

@Component
public class GoogleSMTPTemplate {

    public Message createMessage(Session session, String sender, String receiver, String title, String url ){
        try{
            Message message = new MimeMessage( session );

            message.setFrom( new InternetAddress( sender, title ) );
            message.setRecipients( Message.RecipientType.TO, InternetAddress.parse( receiver ) );
            message.setSubject( "비밀번호 재설정 주소입니다." );

            // HTML 형식 본문 전달
            message.setContent( htmlContent(url), "text/html; charset=UTF-8" );
            return message;
        }
        catch( Exception e ){
            System.out.println( e.getMessage() );
            return null;
        }
    }

    private String htmlContent( String url ){
        return """
            <html>
            <body style='font-family: Arial, sans-serif; margin: 0; padding: 20px;'>
                <div style='max-width: 600px; margin: 0 auto; border: 1px solid #ddd; border-radius: 10px; padding: 30px;'>
                    <h2 style='color: #333; text-align: center;'> 비밀번호 재설정 </h2>
                    <p style='color: #666; font-size: 16px; line-height: 1.5;'>
                        안녕하세요.<br>
                        비밀번호 재설정을 위한 URL을 전달드립니다.
                    </p>
                    <div style='background-color: #f8f9fa; padding: 20px; border-radius: 5px; text-align: center; margin: 20px 0;'>
                        <a style='color: #007bff; font-size: 20px; margin: 0; letter-spacing: 5px;'>
                            https://fooding.im/%s
                        </a>
                    </div>
                    <p style='color: #666; font-size: 14px;'>
                        • 인증번호는 <strong>20분간</strong> 유효합니다.<br>
                        • 본인이 요청하지 않은 정보라면 고객센터에 문의해주시기 바랍니다.
                    </p>
                    <hr style='border: none; border-top: 1px solid #eee; margin: 20px 0;'>
                    <p style='color: #999; font-size: 12px; text-align: center;'>
                        본 메일은 발신전용입니다.
                    </p>
                </div>
            </body>
            </html>
            """.formatted(url);
    }
}
