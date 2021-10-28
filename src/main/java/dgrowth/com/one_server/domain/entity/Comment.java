package dgrowth.com.one_server.domain.entity;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    private User user;

    @ManyToOne(fetch = LAZY)
    private Gallery gallery;

    private String content;
}
