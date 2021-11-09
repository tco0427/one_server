package dgrowth.com.one_server.major.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MajorRequest {

    private String name; // 학과명

    private String location; // 위치

    private String telephoneNumber; // 전화번호

    private String homepageUrl; // 홈페이지

    private String association; // 학생회

    private String president; // 학생회장
}
