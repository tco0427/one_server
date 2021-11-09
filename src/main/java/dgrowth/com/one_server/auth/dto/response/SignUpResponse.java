package dgrowth.com.one_server.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import dgrowth.com.one_server.user.dto.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignUpResponse {

    @JsonProperty("user")
    private UserResponse userResponse;

    @JsonProperty("token")
    private TokenResponse tokenResponse;
}
