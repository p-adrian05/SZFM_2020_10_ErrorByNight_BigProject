package szfm.errorbynight.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="user_messages")
public class MessageDetails {

    @Id
    private Long messageId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Message message;

    @Column(nullable = false)
    private Long sender_Id;
    @Column(nullable = false)
    private Long receiver_Id;

    public MessageDetails(Long sender_Id,Long receiver_Id){
        this.sender_Id = sender_Id;
        this.receiver_Id = receiver_Id;
    }
}
