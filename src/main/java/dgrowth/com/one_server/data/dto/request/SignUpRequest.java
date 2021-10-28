package dgrowth.com.one_server.data.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import dgrowth.com.one_server.domain.enumeration.Authority;
import dgrowth.com.one_server.domain.enumeration.Gender;
import dgrowth.com.one_server.domain.enumeration.PlatformType;
import dgrowth.com.one_server.domain.entity.User;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignUpRequest {

    // 가입 플랫폼 타입(카카오, 네이버)
    private PlatformType platformType;

    // 플랫폼 고유 아이디
    private String platformId;

    // 이름
    private String name;

    // 이메일
    private String email;

    // 성별
    private Gender gender;

    // 생년월일(yymmdd)
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate birth;

    // 프로필 이미지
    private String profileImageUrl;

    // 학생증 이미지
    private String idCardImageUrl;

    public User toUser() {
        return new User(platformType, platformId, name, email, profileImageUrl, idCardImageUrl,
            gender, birth, Authority.USER);
    }
}
