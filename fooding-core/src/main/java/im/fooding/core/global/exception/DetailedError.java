package im.fooding.core.global.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DetailedError {
    private String location;
    private String message;

    @Builder
    public DetailedError(String location, String message) {
        this.location = location;
        this.message = message;
    }
}
