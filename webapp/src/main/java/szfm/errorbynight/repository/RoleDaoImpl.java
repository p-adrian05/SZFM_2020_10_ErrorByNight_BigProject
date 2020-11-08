package szfm.errorbynight.repository;


import org.springframework.stereotype.Repository;
import szfm.errorbynight.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class RoleDaoImpl implements RoleDao{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Role> findByRole(String role) {
        try {
            return Optional.of(entityManager.createQuery("SELECT r FROM Role r WHERE r.role = :role", Role.class)
                    .setParameter("role", role).getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Long add(Role entity) {
        entityManager.persist(entity);
        return entity.getId();
    }
    /**
     * Returns the entity instance with the specified primary key from the
     * database. The method returns an empty {@link Optional} object when
     * the instance does not exists.
     *
     * @param id the primary key to look for
     * @return an {@link Optional} object wrapping the entity instance with
     * the specified primary key
     */
    @Override
    public Optional<Role> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Role.class,id));
    }
    /**
     * Returns a {@link Optional} list of all instances of the entity class from the database.
     *
     * @return an {@link Optional} object wrapping the list of all instances of the entity class from the database
     */
    @Override
    public Optional<List<Role>> findAll() {
        return Optional.ofNullable(entityManager
                .createQuery("SELECT u FROM Role u",Role.class).getResultList());
    }
    /**
     * Removes the specified entity instance from the database.
     *
     * @param entity the entity instance to be removed from the database
     */
    @Override
    public void remove(Role entity) {
        entityManager.remove(entity);
    }
    /**
     * Updates the specified entity instance in the database.
     *
     * @param entity the entity instance to be updated in the database
     */
    @Override
    public void save(Role entity) {
        entityManager.merge(entity);
    }
}
