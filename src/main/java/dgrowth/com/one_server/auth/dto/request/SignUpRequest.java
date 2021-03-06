package dgrowth.com.one_server.auth.dto.request;

import dgrowth.com.one_server.user.domain.enumeration.Authority;
import dgrowth.com.one_server.user.domain.enumeration.PlatformType;
import dgrowth.com.one_server.user.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

@Data
@Slf4j
@AllArgsConstructor
public class SignUpRequest {

    // 가입 플랫폼 타입(카카오, 네이버)
    private PlatformType platformType;

    // 플랫폼 고유 아이디
    private String platformId;

    private String profileImageUrl;

    // 이름
    private String name;

    // 이메일
    private String email;

    // 학생증 이미지
    private MultipartFile idCardImage;

    public User toUser(String idCardImageUrl) {
        return new User(platformType, platformId, name, email, profileImageUrl, idCardImageUrl,
            Authority.USER);
    }
}
