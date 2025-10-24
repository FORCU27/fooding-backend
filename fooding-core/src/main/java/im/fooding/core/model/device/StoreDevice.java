package im.fooding.core.model.device;

import im.fooding.core.model.BaseEntity;
import im.fooding.core.model.store.Store;
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
public class StoreDevice extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Device device;

    @Column( name = "service_type" )
    @Enumerated( EnumType.STRING )
    private ServiceType serviceType;

    @Column( name = "last_connected_at" )
    private LocalDateTime lastConnectedAt;

    @Builder
    public StoreDevice(
        Store store,
        Device device,
        ServiceType type
    ){
        this.store = store;
        this.device = device;
        this.serviceType = type;
        this.lastConnectedAt = LocalDateTime.now();
    }

    public void updateServiceType( ServiceType type ){
        this.serviceType = type;
    }

    public void connectDevice(){
        this.lastConnectedAt = LocalDateTime.now();
    }

}