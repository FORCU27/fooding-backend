package im.fooding.core.model.device;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table( name = "device_log" )
@Getter
@Setter
@NoArgsConstructor
public class DeviceLog {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;
    private long deviceId;
    private String date;
    private String time;
    private String operation;

    @Builder
    public DeviceLog( long deviceId, String operation ) {
        this.deviceId = deviceId;
        LocalDateTime now = LocalDateTime.now();
        String date = now.getYear() + "-" + now.getMonthValue() + "-" + now.getDayOfMonth();
        String time = now.getHour() + ":" + now.getMinute() + ":" + now.getSecond();
        this.date = date;
        this.time = time;
        this.operation = operation;
    }
}
