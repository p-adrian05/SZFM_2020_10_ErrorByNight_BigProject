package szfm.errorbynight.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class UserData {

    @Id
    private Long userId;

    private String city;

    private String publicEmail;

    private String profileImg;

    private String fullName;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "userId")
    private User user;

    public UserData(){
        this.city = "";
        this.publicEmail = "";
        this.fullName = "";
        this.profileImg = "default_profile_img.png";
    }
}
