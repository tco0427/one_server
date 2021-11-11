package dgrowth.com.one_server.chat.dto.response;

import dgrowth.com.one_server.major.domain.entity.Major;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatUserResponse {

    private Long id;

    private String name;

    private String profileImageUrl;
}
