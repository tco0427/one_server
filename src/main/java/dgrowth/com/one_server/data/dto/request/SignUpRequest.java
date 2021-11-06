package dgrowth.com.one_server.data.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import dgrowth.com.one_server.domain.enumeration.Authority;
import dgrowth.com.one_server.domain.enumeration.Gender;
import dgrowth.com.one_server.domain.enumeration.PlatformType;
import dgrowth.com.one_server.domain.entity.User;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Base64;
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

    private MultipartFile profileImage;

    // 이름
    private String name;

    // 이메일
    private String email;

    // 학생증 이미지
    private String idCardImage;

    public User toUser(String profileUrl) {
        return new User(platformType, platformId, name, email, profileUrl,
            Authority.USER);
    }
}
