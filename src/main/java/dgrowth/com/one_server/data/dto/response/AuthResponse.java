package dgrowth.com.one_server.data.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    // 회원 여부
    @JsonProperty("isUser")
    private boolean isUser;

    @JsonProperty("token")
    private TokenResponse tokenResponse;

    @JsonProperty("user")
    private UserResponse userResponse;

    @JsonProperty("oauth")
    private OAuthResponse OAuthResponse;
}
