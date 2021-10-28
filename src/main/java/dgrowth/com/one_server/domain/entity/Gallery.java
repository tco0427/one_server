package dgrowth.com.one_server.domain.entity;

import lombok.Getter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter
public class Gallery {

    @Id
    @GeneratedValue
    private Long id;

    private String imageUrl;

    private String title;

    private String content;

    @ManyToOne(fetch = LAZY)
    private Group group;

    @OneToMany(mappedBy = "gallery")
    private List<Comment> comments = new ArrayList<>();
}
