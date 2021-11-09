package dgrowth.com.one_server.major.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeMajorResponse {
    private Long id;
    private String title;
    private String profileUrl;
}
