package im.fooding.app.dto.response.crawling;

import im.fooding.core.model.store.document.CatchTableStoreDocument;
import im.fooding.core.model.store.document.StoreDocument;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetStoreDocumentResponse {
    private String id;
    private String name;
    private String category;
    private String address;
    private String city;
    private String description;
    private String priceCategory;
    private String direction;
    private String information;
    private List<GetStoreMenuDocumentResponse> menuList;

    public static GetStoreDocumentResponse of(CatchTableStoreDocument document){
        return GetStoreDocumentResponse.builder()
                .id(document.getId())
                .name(document.getName())
                .category(document.getCategory())
                .address(document.getAddress())
                .city(document.getCity())
                .description(document.getDescription())
                .priceCategory(document.getPriceCategory())
                .direction(document.getDirection())
                .information(document.getInformation())
                .build();
    }
}
