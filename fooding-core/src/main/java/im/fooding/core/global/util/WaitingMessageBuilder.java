package im.fooding.core.global.util;

public class WaitingMessageBuilder {
    public static String buildRegisterMessage(String store, int totalPersonCount, int order, int waitingNumber) {
        return """
            title
            푸딩 웨이팅 등록 완료

            body
            안녕하세요 고객님!
            [%s]에 웨이팅이 등록되었습니다. 입장 순서가 되면 안내 메시지를 보내드릴게요.

            - 인원: %d명
            - 순서: %d번째
            - 웨이팅번호: %d
    
            [주의사항]
            - 차례가 되었을 때 현장에 없는 경우 입장이 취소될 수 있습니다.
            """
                .formatted(store, totalPersonCount, order, waitingNumber);
    }

    public static String buildEnterStoreMessage(String sender, String receiver, String store, String notice, int waitingNumber, int limitTime) {
        StringBuilder messageBuilder = new StringBuilder();

        messageBuilder.append("[Web발신]\n")
                .append(String.format("발신: %s\n", sender))
                .append(String.format("수신: %s\n", receiver))
                .append("\n")
                .append(String.format("[Fooding] %s번 고객님, 기다리느라 고생하셨어요! 지금 [%s]에 입장해 주세요.\n", waitingNumber, store))
                .append("\n")
                .append("직원에게 해당 알림톡을 보여주시면 웨이팅 번호 순서대로 안내해 드릴게요.\n")
                .append("\n")
                .append(String.format("%s분으로 입장 시간이 제한되어있어요. 방문이 취소될 수 있으니 꼭 시간을 확인해 주세요.\n", limitTime))
                .append("\n")
                .append("▶ 매장 유의사항\n")
                .append(notice);

        return messageBuilder.toString();
    }

    public static String buildCancel(String store, String reason) {
        return """
               Title
               웨이팅이 취소되었습니다.

               [%s]의 웨이팅이 취소되었어요.

               사유: %s

               다음에 방문해 주세요!
               """
                .formatted(store, reason);
    }
}
