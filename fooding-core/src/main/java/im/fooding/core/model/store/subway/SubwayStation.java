package im.fooding.core.model.store.subway;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class SubwayStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="line")
    private String line;

    @Column(name="address")
    private String address;

    @Builder
    private SubwayStation( String name, String line, String address ){
        this.name = name;
        this.line = line;
        this.address = address;
    }

    @Transactional
    public void update( String stationName ){
        this.name = stationName;
    }
}
