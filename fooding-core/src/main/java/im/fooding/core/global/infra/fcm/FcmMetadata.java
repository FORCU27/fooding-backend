package im.fooding.core.global.infra.fcm;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class FcmMetadata {
    private String type;
    private String imageUrl;
    private String urlPath;
    private String badge;
}