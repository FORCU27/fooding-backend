package im.fooding.app.dto.response.crawling;

import im.fooding.core.model.store.document.StoreMenuDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetStoreMenuDocumentResponse {
    private String id;
    private String storeId;
    private String menuName;
    private String price;

    public static GetStoreMenuDocumentResponse of(StoreMenuDocument document){
        return GetStoreMenuDocumentResponse.builder()
                .id(document.getId())
                .storeId(document.getStoreId())
                .menuName(document.getMenuName())
                .price(document.getPrice())
                .build();
    }
}
