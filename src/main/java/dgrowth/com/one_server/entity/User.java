package dgrowth.com.one_server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
@ToString(of = {"id", "name", "nickname", "email"})
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String name;

    private String nickname;

    private String birth;

    private String profileUrl;

    private Integer platfromId;

    @JsonIgnore
    private boolean activated;

    @Enumerated(STRING)
    private Authority authority;

    public User(String password, String name, String nickname, String birth, String email, boolean activated, Authority authority) {
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.birth = birth;
        this.email = email;
        this.activated =activated;
        this.authority = authority;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String password, String name, String nickname, String email, String birth) {
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.birth = birth;
    }

    public void updateMember(String nickname, String password, String profileUrl) {
        if(nickname != null) {
            this.nickname = nickname;
        }

        if(password != null) {
            this.password = password;
        }

        if(profileUrl != null) {
            this.profileUrl = profileUrl;
        }
    }
}