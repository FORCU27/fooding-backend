package im.fooding.core.model.device;

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
@Table( name = "devices_app" )
public class DeviceApp extends BaseEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @ManyToOne
    @JoinColumn( name="device_id" )
    private Device device;

    @Column( name="version" )
    private String version;

    @Column( name="package_name" )
    private String packageName;

    @Column( name="token" )
    private String token;

    @Column( name="last_connected_at" )
    private LocalDateTime lastConnectedAt;

    @Builder
    public DeviceApp(
            Device device,
            String version,
            String packageName
    ){
        this.device = device;
        this.version = version;
        this.packageName = packageName;
        this.lastConnectedAt = LocalDateTime.now();
    }

    public void refreshToken( String token ){
        this.token = token;
        this.lastConnectedAt = LocalDateTime.now();
    }

    public void updateVersion( String version ){
        this.version = version;
        this.lastConnectedAt = LocalDateTime.now();
    }

}
