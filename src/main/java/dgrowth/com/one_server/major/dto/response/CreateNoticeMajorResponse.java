package dgrowth.com.one_server.major.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateNoticeMajorResponse {
    private Long id;
    private Long majorId;
    private String title;
    private String content;
}
