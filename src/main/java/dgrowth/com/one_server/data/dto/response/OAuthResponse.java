package dgrowth.com.one_server.data.dto.response;

import dgrowth.com.one_server.domain.enumeration.PlatformType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OAuthResponse {

    private PlatformType platformType;

    // 플랫폼 아이디
    private String platformId;

    // 닉네임
    private String nickName;

    // 프로필 이미지
    private String profileImageUrl;

    // 이메일(선택값)
    private String email;
}
