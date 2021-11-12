package dgrowth.com.one_server.major.dto.response;

import dgrowth.com.one_server.notice.dto.response.NoticeResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MajorResponse {

    private Long id;

    private String name; // 학과명

    private String location; // 위치

    private String telephoneNumber; // 전화번호

    private String homepageUrl; // 홈페이지

    private String association; // 학생회

    private String president; // 학생회장

    @Setter
    private List<NoticeResponse> notices;
}
