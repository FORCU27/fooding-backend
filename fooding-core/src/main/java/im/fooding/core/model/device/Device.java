package im.fooding.core.model.device;

import im.fooding.core.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Table( name = "devices" )
public class Device extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "store_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
//    private Store store;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private DeviceType type;

    @Column(name = "installed_at")
    private LocalDateTime installedAt;

    @Column(name = "last_connected_at")
    private LocalDateTime lastConnectedAt;

    @Column(name = "os_version")
    private String osVersion;

    @Column(name = "service_type")
    @Enumerated(EnumType.STRING)
    private ServiceType serviceType;

    @Column(name = "status")
    private boolean status;

    public void update(String name) {
        this.name = name;
    }

    public void updateOsVersion( String osVersion ){
        this.osVersion = osVersion;
    }

    public void changeServiceType( ServiceType serviceType ){
        this.serviceType = serviceType;
    }

    public void connectDevice() {
        this.status = true;
        this.lastConnectedAt = LocalDateTime.now();
    }

    public void disconnectDevice() {
        this.status = false;
    }
}