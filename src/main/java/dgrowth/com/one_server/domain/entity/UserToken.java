package dgrowth.com.one_server.domain.entity;

import javax.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor
@ToString
public class UserToken extends BaseEntity{
    // 토큰
    private String token;

    // 리프레시 토큰
    private String refreshToken;
}
