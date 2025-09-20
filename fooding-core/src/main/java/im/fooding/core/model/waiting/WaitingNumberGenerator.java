package im.fooding.core.model.waiting;

import com.querydsl.core.annotations.QueryEntity;
import im.fooding.core.model.BaseDocument;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "waiting_number_sequence")
@QueryEntity
public class WaitingNumberGenerator extends BaseDocument {

    @Id
    private ObjectId id;

    private long storeId;

    private int lastAssignedNumber;

    public WaitingNumberGenerator(long storeId) {
        this.storeId = storeId;
        lastAssignedNumber = 0;
    }

    public void resetCallNumber() {
        lastAssignedNumber = 0;
    }

    public int issueCallNumber() {
        return ++lastAssignedNumber;
    }
}
