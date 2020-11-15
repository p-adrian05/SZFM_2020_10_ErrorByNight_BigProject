package szfm.errorbynight.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="topics")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false,unique = true)
    private String title;

    @Size()
    private String description;

    @Column(nullable = false)
    private String timestamp;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "category_id")
    private ForumCategory forumCategory;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "founder_id")
    private User founderUser;

    @Column(nullable = false)
    private String lastActiveTimestamp;

    public Topic(String title){
        this.title = title;
        this.description = "";
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss"));
        this.lastActiveTimestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss"));
    }
      @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Topic topic = (Topic) o;
        return Objects.equals(id, topic.id) &&
                Objects.equals(title, topic.title);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}
