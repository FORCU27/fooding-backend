package im.fooding.core.global.util;

public class RewardMessageBuilder {
    public static String buildRegisterMessage( String storeName, int point ){
        return """
                title
                푸딩 리워드 적립 완료
                
                boty
                [푸딩]
                축하드립니다! %d포인트가 적립되었어요 🎁
                포인트는 푸딩에서 회원가입 후 사용하실 수 있어요.
                푸딩과 함께 알뜰한 소비 시작해볼까요?
                                
                https://fooding.im/
                """.formatted( point );
    }
}
