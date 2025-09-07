package im.fooding.core.model.plan;

import com.querydsl.core.annotations.QueryEntity;
import im.fooding.core.model.BaseDocument;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "plan")
@QueryEntity
public class Plan extends BaseDocument {

    @Id
    private ObjectId id;

    private ReservationType reservationType;

    private long userId;

    private long originId;

    private long storeId;

    VisitStatus visitStatus;

    private LocalDateTime reservationTime;

    private int infantChairCount;

    private int infantCount;

    private int adultCount;

    public enum VisitStatus {
        SCHEDULED,
        COMPLETED,
        NOT_VISITED
    }

    public enum ReservationType {
        RESERVATION,
        ONSITE_WAITING,
        ONLINE_WAITING
    }

    @Builder
    public Plan(
        ReservationType reservationType,
        long userId,
        long originId,
        long storeId,
        VisitStatus visitStatus,
        LocalDateTime reservationTime,
        int infantChairCount,
        int infantCount,
        int adultCount
    ) {
        this.reservationType = reservationType;
        this.userId = userId;
        this.originId = originId;
        this.storeId = storeId;
        this.visitStatus = visitStatus;
        this.reservationTime = reservationTime;
        this.infantChairCount = infantChairCount;
        this.infantCount = infantCount;
        this.adultCount = adultCount;
    }
}
