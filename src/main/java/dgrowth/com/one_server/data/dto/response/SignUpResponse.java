package dgrowth.com.one_server.data.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
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
