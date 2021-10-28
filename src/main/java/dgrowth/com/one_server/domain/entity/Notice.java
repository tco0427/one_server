package dgrowth.com.one_server.domain.entity;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@ToString(of = {"id"})
public class Notice {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String title;

    private String content;

    @ManyToOne(fetch = LAZY)
    private Group group;
}
