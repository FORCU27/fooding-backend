package im.fooding.app.dto.request.admin.region;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminRegionBatchCreateRequest {

    @Schema(description = "지역 생성 데이터")
    List<AdminRegionCreateRequest> data;
}
