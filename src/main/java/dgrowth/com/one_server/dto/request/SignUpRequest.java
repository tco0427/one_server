package dgrowth.com.one_server.dto.request;

import dgrowth.com.one_server.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignUpRequest {

    private String name;

    private String nickname;

    private String email;

    private String password;

    private String birth;

    public User toMember() {
        return new User(this.password, this.name, this.nickname, this.email, this.birth);
    }
}