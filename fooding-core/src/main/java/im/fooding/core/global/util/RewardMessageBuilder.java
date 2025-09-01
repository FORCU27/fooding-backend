package im.fooding.core.global.util;

public class RewardMessageBuilder {

    public static String buildMessage(String subject, String content) {
        return """
                title
                %s
                boty
                %s
                """.formatted(subject, content);
    }
}
