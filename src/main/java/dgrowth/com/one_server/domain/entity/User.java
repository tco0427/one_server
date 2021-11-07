package dgrowth.com.one_server.domain.entity;

import dgrowth.com.one_server.data.dto.response.UserResponse;
import dgrowth.com.one_server.domain.enumeration.Authority;
import dgrowth.com.one_server.domain.enumeration.Gender;
import dgrowth.com.one_server.domain.enumeration.PlatformType;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import static javax.persistence.EnumType.*;
import static javax.persistence.FetchType.*;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@ToString(of = {"id", "platformType", "platformId", "name",
        "email", "profileImageUrl", "idCardUrl", "gender",
        "birth", "authority"})
public class User extends BaseEntity{
    @Id
    @GeneratedValue
    private Long id;

    // 가입 플랫폼 타입(카카오, 네이버)
    @Enumerated(STRING)
    private PlatformType platformType;

    // 플랫폼 고유 아이디
    private String platformId;

    // 이름
    private String name;

    // 이메일
    private String email;

    @Setter
    @ManyToOne(fetch = LAZY)
    private Major major;

    // 학번
    private Long studentId;

    // 프로필 이미지
    private String profileImageUrl;

    // 학생증 이미지
    private String idCardUrl;

    // 성별
    @Column(columnDefinition = "varchar(20) default 'FEMALE'")
    @Enumerated(STRING)
    private Gender gender = Gender.FEMALE;

    // 생년월일(yymmdd)
    private LocalDate birth = LocalDate.of(2000, Month.FEBRUARY, 28);

    @Enumerated(STRING)
    private Authority authority;

    @OneToOne
    @JoinColumn(name = "user_token_id")
    private UserToken userToken;

    @OneToMany(mappedBy = "user")
    private List<ParticipantGroup> participantGroups = new ArrayList<>();

    public User(PlatformType platformType, String platformId, String name, String email,
        String profileImageUrl, String idCardUrl, Gender gender, LocalDate birth,
        Authority authority) {
        this.platformType = platformType;
        this.platformId = platformId;
        this.name = name;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
        this.idCardUrl = idCardUrl;
        this.gender = gender;
        this.birth = birth;
        this.authority = authority;
    }

    public User(PlatformType platformType, String platformId, String name, String email,
                String profileImageUrl, String idCardUrl, Authority authority) {
        this.platformType = platformType;
        this.platformId = platformId;
        this.name = name;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
        this.idCardUrl = idCardUrl;
        this.authority = authority;
    }

    public User(PlatformType platformType, String platformId, String name,
        String email, String profileImageUrl,
        Authority authority) {
        this.platformType = platformType;
        this.platformId = platformId;
        this.name = name;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
        this.authority = authority;
    }

    public UserResponse toResponse() {
        return new UserResponse(id, platformType, platformId, name, email, major, profileImageUrl, gender,
            birth);
    }

    public void setUserToken(UserToken userToken) {
        this.userToken = userToken;
    }
}
