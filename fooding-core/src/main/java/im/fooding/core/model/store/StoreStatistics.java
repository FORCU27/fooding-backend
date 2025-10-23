package im.fooding.core.model.store;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "store_statistics")
public class StoreStatistics {

    @Id
    private ObjectId id;

    private long storeId;

    private LocalDate date;

    private int totalSales;

    private double totalSalesChangeRate;

    private int totalVisitors;

    private double visitorChangeRate;

    private double annualTargetSalesRate;

    @Builder
    public StoreStatistics(
            long storeId,
            LocalDate date,
            int totalSales,
            double totalSalesChangeRate,
            int totalVisitors,
            double visitorChangeRate,
            double annualTargetSalesRate
    ) {
        this.storeId = storeId;
        this.date = date;
        this.totalSales = totalSales;
        this.totalSalesChangeRate = totalSalesChangeRate;
        this.totalVisitors = totalVisitors;
        this.visitorChangeRate = visitorChangeRate;
        this.annualTargetSalesRate = annualTargetSalesRate;
    }
}
