package dgrowth.com.one_server.data.dto.response;

import dgrowth.com.one_server.domain.entity.UserToken;
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
