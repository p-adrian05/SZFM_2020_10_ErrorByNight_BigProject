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

      public List<ThemeStat> getThemeStat(String themeName, int lowerLimit, int range) {
        List<ThemeStat> themeStats = new LinkedList<>();
        try {
            List<Tuple> data = entityManager.createNativeQuery("select t.TITLE, count(p.id)," +
                    " u.USERNAME," +
                    " ud.profileImg, t.LASTACTIVETIMESTAMP, t.TIMESTAMP" +
                    " from TOPICS t left join POSTS p " +
                    "on t.ID = p.TOPIC_ID join FORUM_CATEGORIES fc " +
                    "on fc.ID = t.CATEGORY_ID " +
                    "join USERS u on t.FOUNDER_ID = u.ID " +
                    "join USERDATA ud on t.FOUNDER_ID = ud.USERID " +
                    "where fc.TITLE =:themeName " +
                    "group by t.ID " +
                    "order by t.LASTACTIVETIMESTAMP", Tuple.class)
                    .setParameter("themeName", themeName)
                    .setFirstResult(lowerLimit)
                    .setMaxResults(range)
                    .getResultList();
            data.forEach((tuple) -> {
                themeStats.add(
                        ThemeStat.builder()
                                .title(String.valueOf(tuple.get(0)))
                                .topicCount(Integer.parseInt(String.valueOf(tuple.get(1))))
                                .founderUsername(String.valueOf(tuple.get(2)))
                                .founderUserProfileImageName(String.valueOf(tuple.get(3)))
                                .lastActive(String.valueOf(tuple.get(4)))
                                .timestamp(String.valueOf(tuple.get(5)))
                                .build()
                );
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return themeStats;
        }
        return themeStats;
    }

     public Optional<Topic> getTopicByName(String topicName) {
        try {
            return Optional.of(entityManager
                    .createQuery("SELECT t FROM Topic t WHERE t.title =: topicName", Topic.class)
                    .setParameter("topicName", topicName)
                    .getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

     public Optional<Long> getCategoryIdByName(String categoryName) {
        try {
            return Optional.ofNullable(entityManager
                    .createQuery("SELECT c.id FROM ForumCategory c WHERE c.title =: categoryName", Long.class)
                    .setParameter("categoryName", categoryName).getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }



}