package dgrowth.com.one_server.auth.dto.response;

import dgrowth.com.one_server.auth.domain.entity.UserToken;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenResponse {
    private String accessToken;
    private String refreshToken;

    public UserToken toUserToken(){
        return new UserToken(accessToken, refreshToken);
    }
}
