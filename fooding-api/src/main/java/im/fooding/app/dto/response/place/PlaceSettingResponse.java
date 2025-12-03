package im.fooding.app.dto.response.place;

import im.fooding.core.model.place.PlaceSetting;
import im.fooding.core.model.place.PlaceSettingBody;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaceSettingResponse {

    private Long id;
    private String metadata;
    private String headerTitle;
    private Body body;

    public static PlaceSettingResponse from(PlaceSetting placeSetting) {
        return PlaceSettingResponse.builder()
                .id(placeSetting.getId())
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
