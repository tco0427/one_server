package dgrowth.com.one_server.domain.entity;

import dgrowth.com.one_server.data.enumeration.Gender;
import dgrowth.com.one_server.data.enumeration.PlatformType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor
@ToString
public class User extends BaseEntity{
    // 가입 플랫폼 타입(카카오, 네이버)
    @Enumerated(EnumType.STRING)
    private PlatformType platformType;

    // 플랫폼 고유 아이디
    private Integer platformId;

    // 이름
    private String name;

    // 이메일
    private String email;

    // 비밀번호
    private String password;

    // 프로필 이미지
    private String profileImageUrl;

    // 학생증 이미지
    private String idCardUrl;

    // 성별
    @Enumerated(EnumType.STRING)
    private Gender gender;

    // 생년월일(yymmdd)
    private int birth;

    @OneToOne
    @JoinColumn(name = "user_token_id")
    private UserToken userToken;
}
