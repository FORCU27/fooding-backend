package im.fooding.app.dto.request.ceo.store;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CeoUpdateStoreNameRequest {
    @NotBlank(message = "가게 이름은 필수입니다.")
    @Schema(description = "가게명", example = "홍가네")
    private String name;
}
