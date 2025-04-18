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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private DeviceType type;

    @Column(name = "os_version")
    private String osVersion;

    @Column(name = "status")
    private boolean status;

    @Builder
    public Device(
            String name,
            DeviceType type,
            String osVersion
    ){
        this.name = name;
        this.type = type;
        this.osVersion = osVersion;
        this.status = true;
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
    }

    public void disconnectDevice() {
        this.status = false;
    }
}