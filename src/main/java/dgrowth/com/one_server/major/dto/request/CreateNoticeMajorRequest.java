package dgrowth.com.one_server.major.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateNoticeMajorRequest {
    private Long majorId;
    private String title;
    private String content;
}
