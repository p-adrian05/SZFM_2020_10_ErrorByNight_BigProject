package szfm.errorbynight.repository;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import szfm.errorbynight.model.*;
import szfm.errorbynight.util.ThemeStat;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of {@link UserDao} interface.
 */
@Repository
@Slf4j
@Transactional
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<User> findByUsername(String username) {
        try {
            return Optional
                    .of(entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                            .setParameter("username", username).getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Long> getIdByUsername(String username) {
        try {
            return Optional
                    .of(entityManager.createQuery("SELECT u.id FROM User u WHERE u.username = :username", Long.class)
                            .setParameter("username", username).getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Long> getIdByEmail(String email) {
        try {
            return Optional.of(entityManager.createQuery("SELECT u.id FROM User u WHERE u.email = :email", Long.class)
                    .setParameter("email", email).getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByActivation(String code) {
        try {
            return Optional
                    .of(entityManager.createQuery("SELECT u FROM User u WHERE u.activation = :activation", User.class)
                            .setParameter("activation", code).getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Long add(User entity) {
        entityManager.persist(entity);
        return entity.getId();
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public Optional<List<User>> findAll() {
        return Optional.ofNullable(entityManager.createQuery("SELECT u FROM User u", User.class).getResultList());
    }

    @Override
    public void remove(User entity) {
        entityManager.remove(entity);
    }

    @Override
    public void save(User entity) {
        entityManager.merge(entity);
    }

    @Override
    public void saveUserData(UserData entity) {
        entityManager.merge(entity);
    }

    @Override
    public void sendMessage(Message message) {
        entityManager.persist(message);
    }

    @Override
    public List<String> getConversationUsernames(User user, int lowerLimit, int range) {
        return new LinkedList<>();
    }

    @Override
    public List<Message> getNewMessages(Long senderUserId, Long receiverUserIde) {
        return new LinkedList<>();
    }

    @Override
    public int countNewMessagesForUser(String username1, String username2) {
        return 0;
    }

    @Override
    public Integer getMessagesCount(Long senderId, Long receiverId) {
        Long count;
        try {
            count = entityManager.createQuery("SELECT COUNT(me.messageId) FROM MessageDetails me WHERE (me.sender_Id = :userId1 OR " +
                    "me.receiver_Id =: userId1) AND (me.sender_Id = :userId2 OR me.receiver_Id =: userId2)", Long.class)
                    .setParameter("userId1", senderId)
                    .setParameter("userId2", receiverId)
                    .getSingleResult();
        } catch (Exception e) {
            return 0;
        }
        return count.intValue();
    }

    @Override
    public boolean madeReadedMessages(List<Message> messages) {
        return true;
    }
}
