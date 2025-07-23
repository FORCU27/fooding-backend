package im.fooding.core.model.test;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// todo: PR 확인 후 삭제
@Document(collection = "test")
@Data
public class TestDocument {

    @Id
    private String id;
    private String name;
}
