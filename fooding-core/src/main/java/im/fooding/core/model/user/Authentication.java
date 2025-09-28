package im.fooding.core.model.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table( name = "authentication" )
public class Authentication {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private long id;

    @Column( nullable = false )
    private String email;

    @Column( nullable = false )
    private String phoneNumber;

    @Column( nullable = false )
    private int code;

    @Column( nullable = false )
    private LocalDateTime requestedAt;

    @Column( nullable = false )
    private LocalDateTime expiredAt;

    @Column( nullable = false )
    private boolean isSuccess;

    @Builder
    public Authentication( String email, String phoneNumber, int code ){
        this.email = email;
        this.code = code;
        this.phoneNumber = phoneNumber;
        this.requestedAt = LocalDateTime.now();
        this.expiredAt = LocalDateTime.now().plusMinutes( 20 );     // 현재 제한 시간을 20분으로 제한
        this.isSuccess = false;
    }

    public void success(){ this.isSuccess = true; }
}
