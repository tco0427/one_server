package dgrowth.com.one_server.group.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeGroupResponse {
    private Long id;
    private String title;
    private String groupImageUrl;
}
