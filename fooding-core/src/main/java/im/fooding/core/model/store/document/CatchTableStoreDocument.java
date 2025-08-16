package im.fooding.core.model.store.document;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Builder
@AllArgsConstructor
@Document(indexName = "stores_v2", createIndex = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CatchTableStoreDocument {
    @Id
    @Field(type = FieldType.Text)
    private String id;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Keyword)
    private String city;

    @Field(type = FieldType.Keyword)
    private String category;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Text)
    private String priceCategory;

    @Field(type = FieldType.Text)
    private String direction;

    @Field(type = FieldType.Text)
    private String information;

    @Field(type = FieldType.Text)
    private String address;
}
