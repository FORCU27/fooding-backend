package im.fooding.core.model.store.subway;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Document {
    private String id;
    private String place_name;
    private String category_name;
    private String category_group_code;
    private String category_group_name;
    private String phone;
    private String address_name;
    private String road_address_name;
    private String x;  // 경도
    private String y;  // 위도
    private String place_url;
    private String distance;
}
