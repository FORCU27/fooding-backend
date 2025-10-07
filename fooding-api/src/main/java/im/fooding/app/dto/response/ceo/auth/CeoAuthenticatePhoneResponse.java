package im.fooding.app.dto.response.ceo.auth;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class CeoAuthenticatePhoneResponse {
    private boolean isSuccess;
    private String email;
    private String phoneNumber;
    private int code;

    @Builder
    public CeoAuthenticatePhoneResponse(
            boolean isSuccess, String email, String phoneNumber, int code
    ){
        this.isSuccess = isSuccess;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.code = code;
    }
}
