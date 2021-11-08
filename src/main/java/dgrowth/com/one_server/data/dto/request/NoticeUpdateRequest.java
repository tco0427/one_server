package dgrowth.com.one_server.data.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NoticeUpdateRequest {
    private String title;
    private String content;
}
