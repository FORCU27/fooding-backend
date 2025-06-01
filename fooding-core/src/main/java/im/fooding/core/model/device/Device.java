package im.fooding.core.model.device;

import im.fooding.core.model.BaseEntity;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.user.User;
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
@Table( name = "devices" )
public class Device extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", nullable = false)
    private String uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private DevicePlatform type;

    @Column(name = "os_version")
    private String osVersion;

    @Column(name = "package_name")
    private String packageName;

    @Column(name = "status")
    private boolean status;

    @Column(name = "installed_at")
    private LocalDateTime installedAt;

    @Column(name = "last_connected_at")
    private LocalDateTime lastConnectedAt;

    @Builder
    public Device(
            String uuid,
            Store store,
            String name,
            DevicePlatform type,
            String osVersion,
            String packageName
    ){
        this.uuid = uuid;
        this.store = store;
        this.name = name;
        this.type = type;
        this.osVersion = osVersion;
        this.packageName = packageName;
        this.status = true;
        this.installedAt = LocalDateTime.now();
        this.lastConnectedAt = LocalDateTime.now();
    }

    public void update(String name) {
        this.name = name;
    }

    public void updateOsVersion( String osVersion ){
        this.osVersion = osVersion;
    }

    public void updateUser( User user ) { this.user = user; }

    public void connectDevice() {
        this.status = true;
        this.lastConnectedAt = LocalDateTime.now();
    }

    public void disconnectDevice() {
        this.status = false;
    }
}