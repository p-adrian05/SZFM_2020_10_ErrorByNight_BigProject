package szfm.errorbynight.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @Size(max = 1000)
    private String messageContent;
    @Column(nullable = false)
    private String timestamp;
    @Column(nullable = false)
    private boolean status;

    @Transient
    private String senderUsername;
    @Transient
    private String receiverUsername;
    @Transient
    private String senderUserData;
    @Transient
    private String receiverUserData;

    @OneToOne(mappedBy = "message",cascade=CascadeType.ALL,fetch = FetchType.LAZY)
    private MessageDetails messageDetails;

    public Message(String messageContent){
        this.messageContent = messageContent;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss"));
        this.status = false;
    }
}
