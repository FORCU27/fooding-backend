package im.fooding.core.global.infra.fcm;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class SendFcmDto {
    private String title;
    private String content;
    private FcmMetadata metadata;
}