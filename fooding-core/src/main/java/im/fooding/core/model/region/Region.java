package im.fooding.core.model.region;

import im.fooding.core.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class Region extends BaseEntity {

    @Id
    @Column(name = "id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "parent_region_id")
    private Region parentRegion;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "timezone", nullable = false)
    private String timezone;

    @Column(name = "country", nullable = false)
    private String countryCode;

    @Column(name = "legal_code", nullable = false)
    private String legalCode;

    @Column(name = "currency", nullable = false)
    private String currency;

    @Column(name = "level", nullable = false)
    private Integer level;

    @Builder
    public Region(
            String id,
            Region parentRegion,
            String  name,
            String timezone,
            String countryCode,
            String legalCode,
            String currency,
            Integer level
    ) {
        this.id = id;
        this.parentRegion = parentRegion;
        this.name = name;
        this.timezone = timezone;
        this.countryCode = countryCode;
        this.legalCode = legalCode;
        this.currency = currency;
        this.level = level;
    }

    public void update(
            Region parentRegion,
            String name,
            String timezone,
            String countryCode,
            String legalCode,
            String currency,
            Integer level
    ) {
        this.parentRegion = parentRegion;
        this.name = name;
        this.timezone = timezone;
        this.countryCode = countryCode;
        this.legalCode = legalCode;
        this.currency = currency;
        this.level = level;
    }
}
