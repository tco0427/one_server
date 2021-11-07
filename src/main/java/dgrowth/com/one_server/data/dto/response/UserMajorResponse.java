package dgrowth.com.one_server.data.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMajorResponse {
    // 회원 id
    private Long id;

    // 이름
    private String name;

    // 프로필 이미지
    private String profileImageUrl;
}
