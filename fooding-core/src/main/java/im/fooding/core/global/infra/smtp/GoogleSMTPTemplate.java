package im.fooding.core.global.infra.smtp;

import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.stereotype.Component;

@Component
public class GoogleSMTPTemplate {

    public Message createMessage(Session session, String sender, String receiver, String title, String url, String name, String expiredAt ){
        try{
            Message message = new MimeMessage( session );

            message.setFrom( new InternetAddress( sender, title ) );
            message.setRecipients( Message.RecipientType.TO, InternetAddress.parse( receiver ) );
            message.setSubject( "비밀번호 재설정 주소입니다." );

            // HTML 형식 본문 전달
            message.setContent( htmlContent(name, url, expiredAt), "text/html; charset=UTF-8" );
            return message;
        }
        catch( Exception e ){
            System.out.println( e.getMessage() );
            return null;
        }
    }

    private String htmlContent( String url, String name, String expiredAt ){
        return """
            <html>
            <body style='font-family: Arial, sans-serif; margin: 0; padding: 20px;'>
                <div style='max-width: 600px; margin: 0 auto; border: 1px solid #ddd; border-radius: 10px; padding: 30px;'>
                    <h2 style='color: #333; text-align: center;'> [푸딩 사장님] 비밀번호 변경을 진행해주세요! </h2>
                    <p style='color: #666; font-size: 16px; line-height: 1.5;'>
                        비밀번호 변경 안내 <br>
                        안녕하세요. %s님. <br>
                        아래 버튼을 클릭하셔서 새로운 비밀번호로 변경해주세요.
                    </p>
                    <div style='background-color: #f8f9fa; padding: 20px; border-radius: 5px; text-align: center; margin: 20px 0;'>
                        <a href='https://fooding.im/%s' style='color: #007bff; font-size: 20px; margin: 0; letter-spacing: 5px;'>
                            비밀번호 변경하러 가기
                        </a>
                    </div>
                    <p style='color: #666; font-size: 15px; line-height: 1.5;'>
                        유효시간: %s까지
                    </p>
                    <p style='color: #666; font-size: 14px;'>
                        • 위 비밀번호 변경 링크는 발송 후 <strong> 30분 </strong> 동안 유효합니다.<br>
                        • 30분이 지난 후에는 비밀번호 변경 링크 요청을 다시 진행해주세요.
                    </p>
                    <p style='color: #666; font-size: 14px; line-height: 1.5;'>
                        보다 안전하고 편리한 서비스를 만들기 위해 항상 노력하겠습니다. <br>
                        푸딩 드림
                    </p>
                    <hr style='border: none; border-top: 1px solid #eee; margin: 20px 0;'>
                    <p style='color: #999; font-size: 12px; text-align: center;'>
                        본 메일은 발신전용입니다.
                    </p>
                </div>
            </body>
            </html>
            """.formatted(name, url, expiredAt);
    }
}
