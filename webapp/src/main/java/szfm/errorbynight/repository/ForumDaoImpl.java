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

     public Map<ForumCategory, Integer> getCategoriesAndTopicsCount() {
        Map<ForumCategory, Integer> categoriesAndTopicsCount = new LinkedHashMap<>();
        try {
            entityManager
                    .createQuery("SELECT c as Category, count(t.id) as counts FROM ForumCategory c left join Topic t on c.id = t.forumCategory.id " +
                            "group by c.title,c.id, c.description order by counts desc", Tuple.class)
                    .getResultStream().forEachOrdered(tuple -> {
                        categoriesAndTopicsCount.put(((ForumCategory) tuple.get("Category")),
                                ((Number) tuple.get("counts")).intValue());
                    }
            );
        } catch (Exception e) {
            return categoriesAndTopicsCount;
        }
        return categoriesAndTopicsCount;

    }
}