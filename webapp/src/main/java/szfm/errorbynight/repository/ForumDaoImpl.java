package szfm.errorbynight.repository;

import org.springframework.stereotype.Repository;
import szfm.errorbynight.model.ForumCategory;
import szfm.errorbynight.model.Post;
import szfm.errorbynight.util.ThemeStat;
import szfm.errorbynight.model.Topic;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.transaction.Transactional;
import java.util.*;

@Repository
@Transactional
public class ForumDaoImpl implements ForumDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Optional<ForumCategory> getCategoryByName(String categoryName) {
        try {
            return Optional.of(entityManager
                    .createQuery("SELECT c FROM ForumCategory c WHERE c.title =: categoryName", ForumCategory.class)
                    .setParameter("categoryName", categoryName)
                    .getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }

    }
}