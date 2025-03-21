package im.fooding.core.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class ApiResult<T> {
    @Schema(description = "결과", example = "OK")
    private HttpStatus status = HttpStatus.OK;

    @Schema(description = "결과데이터")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    private ApiResult(T data) {
        this.data = data;
    }

    public static <T> ApiResult<T> ok() {
        return new ApiResult<>(null);
    }

    public static <T> ApiResult<T> ok(T data) {
        return new ApiResult<>(data);
    }
}
