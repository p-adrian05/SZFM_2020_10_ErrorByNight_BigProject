package szfm.errorbynight.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NaturalId
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String activation;

    @Column(nullable = false)
    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<Role> roles = new HashSet<>();

    @Column(nullable = false)
    private ZonedDateTime created;

    private Date lastLogin;

    public User(String username, String email, String password) {
        this.username = username;
        this.email=email;
        this.password = password;

    }

    @OneToOne(mappedBy = "user",fetch = FetchType.LAZY)
    private UserData userData;

    @PrePersist
    protected void onPersist() {
        created = ZonedDateTime.now();
    }

    public void addRole(Role role){
        if (roles.contains(role)){
            return ;
        }
        roles.add(role);
        //role.getUsers().add(this);
    }
    public void removeRole(Role role){
        this.roles.remove(role);
        //role.getUsers().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;
        User user = (User) o;
        return Objects.equals(this.username, user.getUsername());
    }

    @Override
    public int hashCode() {
        return this.username.hashCode();
    }
}
