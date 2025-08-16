package im.fooding.app.dto.request.crawling.catchtable;

import lombok.*;

import java.util.List;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CatchTableStoreRequest {
    private String storeName;
    private String description;
    private String address;
    private String priceCategory;
    private String information;
    private String region;
    private String category;
    private String direction;
}
