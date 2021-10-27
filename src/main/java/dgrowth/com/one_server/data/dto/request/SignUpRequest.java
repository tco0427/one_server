package dgrowth.com.one_server.data.dto.request;

import dgrowth.com.one_server.data.enumeration.Authority;
import dgrowth.com.one_server.data.enumeration.PlatformType;
import dgrowth.com.one_server.domain.entity.User;
import java.util.Arrays;
import java.util.Base64;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
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

    // 프로필 이미지
    private String profileImageUrl;

    // 학생증 이미지
    private String idCardImage;

    public void decodeIdCardImage() {
        idCardImage = Arrays.toString(Base64.getDecoder().decode(idCardImage));
        log.error("decodeIdCardImage : " + idCardImage);
    }

    public User toUser() {
        decodeIdCardImage();
        return new User(platformType, platformId, name, email, profileImageUrl, idCardImage,
            Authority.USER);
    }
}
