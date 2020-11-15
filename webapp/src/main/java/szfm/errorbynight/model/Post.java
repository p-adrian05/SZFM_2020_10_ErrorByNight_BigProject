package szfm.errorbynight.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Size(max = 1500)
    private String content;

    @Column(nullable = false)
    private String timestamp;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parentPost_id",referencedColumnName = "id")
    private Post parentPost;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",referencedColumnName = "id",nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id",referencedColumnName = "id",nullable = false)
    private Topic topic;
    @Column()
    private int parentPostOffset;
}