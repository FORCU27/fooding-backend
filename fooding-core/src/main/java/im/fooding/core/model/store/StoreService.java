package im.fooding.core.model.store;

import im.fooding.core.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Table(name = "store_service")
public class StoreService extends BaseEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @ManyToOne
    @JoinColumn( name = "store_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT) )
    private Store store;

    @Column( name = "type" )
    @Enumerated( EnumType.STRING )
    private StoreServiceType type;

    @Column( name = "activation" )
    private boolean activation;

    private LocalDateTime endedAt;

    @Builder
    public StoreService(
            Store store,
            StoreServiceType type
    ){
        this.store = store;
        this.type = type;
        this.activation = true;
    }

    public void activate(){
        this.activation = true;
        this.endedAt = null;
    }
    public void inactive(){
        this.activation = false;
        this.endedAt = LocalDateTime.now();
    }
}
