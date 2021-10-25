package dgrowth.com.one_server.dto.request;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequest {
    @NotNull
    private String email;
    @NotNull
    private String password;
}