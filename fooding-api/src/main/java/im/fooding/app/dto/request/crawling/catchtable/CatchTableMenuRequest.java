package im.fooding.app.dto.request.crawling.catchtable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CatchTableMenuRequest {
    private String storeId;
    private String menuName;
    private String price;
}
