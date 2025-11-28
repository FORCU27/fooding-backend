package im.fooding.app.dto.response.ceo.place;

import im.fooding.core.model.place.PlaceSetting;
import im.fooding.core.model.place.PlaceSettingBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CeoPlaceSettingResponse {

    @Schema(description = "플레이스 세팅 ID", example = "1")
    private Long id;

    @Schema(description = "가게 ID", example = "1")
    private Long storeId;

    @Schema(description = "메타데이터")
    private String metadata;

    @Schema(description = "헤더 제목")
    private String headerTitle;

    private Body body;

    public static CeoPlaceSettingResponse from(PlaceSetting placeSetting) {
        return CeoPlaceSettingResponse.builder()
                .id(placeSetting.getId())
                .storeId(placeSetting.getStore().getId())
                .metadata(placeSetting.getMetadata())
                .headerTitle(placeSetting.getHeaderTitle())
                .body(Body.from(placeSetting.getBody()))
                .build();
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Body {
        private boolean enabled;
        private String content;

        public static Body from(PlaceSettingBody body) {
            if (body == null) {
                return Body.builder()
                        .enabled(false)
                        .content(null)
                        .build();
            }
            return Body.builder()
                    .enabled(body.isEnabled())
                    .content(body.getContent())
                    .build();
        }
    }
}
