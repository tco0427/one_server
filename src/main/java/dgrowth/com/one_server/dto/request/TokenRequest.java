package dgrowth.com.one_server.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenRequest {

    private String accessToken;

    private String refreshToken;
}