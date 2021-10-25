package dgrowth.com.one_server.dto.response;

import dgrowth.com.one_server.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String email;
    private String name;
    private String birth;
    private String nickname;

    public UserResponse(User user){
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.birth = user.getBirth();
        this.nickname = user.getNickname();
    }
}
