package im.fooding.core.global.util;

public class WaitingMessageBuilder {
    public static String buildWaitingMessage(String sender, String receiver, String store, String notice, int personnel, int myOrder, int waitingNumber, int delayWaitingNumber) {
        StringBuilder messageBuilder = new StringBuilder();

        messageBuilder.append("[Web발신]\n")
                .append(String.format("발신: %s\n", sender))
                .append(String.format("수신: %s\n", receiver))
                .append("\n")
                .append("[Fooding] 안녕하세요, 고객님!\n")
                .append(String.format("[%s]에 웨이팅이 등록되었습니다. 입장 순서가 되면 안내 메시지를 보내드릴게요.\n", store))
                .append("\n")
                .append("곧 입장 안내를 드릴 예정이니 매장 근처에서 기다려주세요.\n")
                .append("\n")
                .append("▶ 인원: ").append(personnel).append("명\n")
                .append("▶ 내 순서: ").append(myOrder).append("번째\n")
                .append("▶ 웨이팅 번호: ").append(waitingNumber).append("\n")
                .append("▶ 매장 공지사항\n")
                .append(notice).append("\n")
                .append("\n")
                .append(String.format("✓ 원하는 순서를 선택해 입장을 %s번 미룰 수 있는 매장입니다. 시간 내 도착이 어렵다면 순서 미루기를 이용해 주세요.", delayWaitingNumber));

        return messageBuilder.toString();
    }

    public static String buildWaitingCallMessage(String store, int callNumber, int entryTimeLimit) {
        return """
                Title
                입장할 순서예요! 지금 매장에 입장해 주세요!
                                
                %d번 고객님, 기다려주셔서 감사해요!
                %s에 입장해 주세요:)
                                
                직원에게 톡 사진을 보여주시면 순서대로 안내드릴게요!
                                
                %d분으로 입장 시간이 제한되어있어요. 방문이 취소될 수 있으니 시간 확인 부탁드려요!
                """
                .formatted(callNumber, store, entryTimeLimit);
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
}
