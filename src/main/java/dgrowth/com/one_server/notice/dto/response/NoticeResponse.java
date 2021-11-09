package dgrowth.com.one_server.notice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NoticeResponse {
    private Long id;
    private String title;
    private String content;
}
