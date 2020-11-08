package szfm.errorbynight.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NaturalId
    private String role;

    //@ManyToMany(mappedBy = "roles",fetch = FetchType.LAZY)
    //private Set<User> users = new HashSet<>();

    public Role(String role) {
        this.role = role;
    }

    //public void addUser(User user){
     //   if (users.contains(user)){
     //       return ;
     //   }
     //   users.add(user);
     //   user.addRole(this);
    //}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;
        return Objects.equals(this.role, role.getRole());
    }

    @Override
    public int hashCode() {
        return role.hashCode();
    }
}
