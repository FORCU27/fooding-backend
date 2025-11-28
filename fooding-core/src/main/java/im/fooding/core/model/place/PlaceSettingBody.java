package im.fooding.core.model.place;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaceSettingBody {

    @Column(name = "body_enabled", nullable = false)
    private boolean enabled;

    @Column(name = "body_content", columnDefinition = "TEXT")
    private String content;

    @Builder
    private PlaceSettingBody(boolean enabled, String content) {
        this.enabled = enabled;
        this.content = content;
    }

    public void update(boolean enabled, String content) {
        this.enabled = enabled;
        this.content = content;
    }
}
