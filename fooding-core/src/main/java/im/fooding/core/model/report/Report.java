package im.fooding.core.model.report;

import im.fooding.core.model.BaseEntity;
import im.fooding.core.model.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Getter
@NoArgsConstructor( access = AccessLevel.PROTECTED )
@DynamicUpdate
@Table( name = "report" )
public class Report extends BaseEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @Column( name = "reference_id" )
    private long referenceId;

    @Column( name = "target_type" )
    @Enumerated( EnumType.STRING )
    private ReportTargetType targetType;

    @Column( name = "description" )
    private String description;

    @Column( name = "memo" )
    private String memo;

    @ManyToOne
    @JoinColumn( name = "reporter_id", nullable = false )
    private User reporter;

    @Column( name = "status" )
    @Enumerated( EnumType.STRING )
    private ReportStatus status;

    @ManyToOne
    @JoinColumn( name = "charger_id", nullable = true )
    private User charger;

    @Builder
    public Report(
            long referenceId, ReportTargetType targetType, String description,
            String memo, User reporter, ReportStatus status, User charger
    ){
        this.referenceId = referenceId;
        this.targetType = targetType;
        this.description = description;
        this.memo = memo;
        this.reporter = reporter;
        this.status = status;
        this.charger = charger;
    }

    @Transactional
    public void updateMemo( String memo ) { this.memo = memo; }

    @Transactional
    public void updateStatus( ReportStatus status ) { this.status = status; }

    @Transactional
    public void updateCharger( User charger ) { this.charger = charger; }
}
