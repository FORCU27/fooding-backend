package im.fooding.core.model.store.document;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Document( indexName = "stores_menu_v1", createIndex = false )
@NoArgsConstructor( access = AccessLevel.PROTECTED )
public class StoreMenuDocument {
    @Id
    @Field(type = FieldType.Text)
    private String id;

    @Field(type = FieldType.Text)
    private String storeId;

    @Field(type = FieldType.Text)
    private String menuName;

    @Field(type = FieldType.Text)
    private String price;

    @Builder
    public StoreMenuDocument( String id, String storeId, String menuName, String price ){
        this.id = id;
        this.storeId = storeId;
        this.menuName = menuName;
        this.price = price;
    }
}
