package dgrowth.com.one_server.data.dto.response;

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
