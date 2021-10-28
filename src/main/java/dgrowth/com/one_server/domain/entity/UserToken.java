package dgrowth.com.one_server.domain.entity;

import dgrowth.com.one_server.data.dto.response.TokenResponse;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@ToString
public class UserToken extends BaseEntity{
    @Id
    @GeneratedValue
    private Long id;

    // 토큰
    @Column(columnDefinition = "varchar(400)")
    private String accessToken;

    // 리프레시 토큰
    @Column(columnDefinition = "varchar(400)")
    private String refreshToken;

    public UserToken(Long id, String accessToken, String refreshToken) {
        this.id = id;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public UserToken(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public TokenResponse toResponse(){
        return new TokenResponse(accessToken, refreshToken);
    }

    public void setToken(String accessToken, String refreshToken){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
