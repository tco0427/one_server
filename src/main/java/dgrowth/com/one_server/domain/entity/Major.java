package dgrowth.com.one_server.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.*;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Major extends BaseEntity{

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name; // 학과명

    private String location; // 위치

    private String telephoneNumber; // 전화번호

    private String homepageUrl; // 홈페이지

    private String association; // 학생회

    private String president; // 학생회장

    private String profileUrl = null; //모든 학과 이미지에 대하여 DKU로고 사용

    @OneToMany(mappedBy = "major")
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "major")
    private List<Notice> notices = new ArrayList<>();
}
