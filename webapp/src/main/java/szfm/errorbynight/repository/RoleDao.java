package szfm.errorbynight.repository;



import szfm.errorbynight.model.Role;

import java.util.Optional;

public interface RoleDao extends GenericDao<Role> {

    Optional<Role> findByRole(String role);
}
