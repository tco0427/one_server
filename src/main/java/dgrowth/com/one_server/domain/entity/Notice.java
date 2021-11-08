package dgrowth.com.one_server.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@ToString(of = {"id", "title", "content"})
public class Notice extends BaseEntity{

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String title;

    private String content;

    @ManyToOne(fetch = LAZY)
    private Group group;

    @ManyToOne(fetch = LAZY)
    private Major major;

    public Notice (String title, String content, Group group) {
        this.title = title;
        this.content = content;
        this.group = group;
        this.major = null;
    }

    public Notice (String title, String content, Major major) {
        this.title = title;
        this.content = content;
        this.major = major;
        this.group = null;
    }

    public void update(String title, String content) {
        if (title != null ) {this.title = title; }
        if (content != null ) {this.content = content; }
    }
}
