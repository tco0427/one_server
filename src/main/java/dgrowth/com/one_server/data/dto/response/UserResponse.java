package dgrowth.com.one_server.data.dto.response;

import dgrowth.com.one_server.domain.enumeration.Gender;
import dgrowth.com.one_server.domain.enumeration.PlatformType;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    // 회원 id
    private Long id;

    // 가입 플랫폼 타입(카카오, 네이버)
    private PlatformType platformType;

    // 플랫폼 고유 아이디
    private String platformId;

    // 이름
    private String name;

    // 이메일
    private String email;

    // 프로필 이미지
    private String profileImageUrl;

    // 성별
    private Gender gender;

    // 생년월일(yymmdd)
    private LocalDate birth;
}
