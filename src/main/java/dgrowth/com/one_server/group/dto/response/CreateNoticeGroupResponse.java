package dgrowth.com.one_server.group.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateNoticeGroupResponse {
    private Long id;
    private Long groupId;
    private String title;
    private String content;
}
