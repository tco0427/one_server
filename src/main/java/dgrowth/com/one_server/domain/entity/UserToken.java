package dgrowth.com.one_server.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor
@ToString
public class UserToken extends BaseEntity{
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    // 토큰
    private String token;

    // 리프레시 토큰
    private String refreshToken;
}
