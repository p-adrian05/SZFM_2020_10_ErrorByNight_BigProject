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
        List<Message> messages = new LinkedList<>();
        try {
            messages = entityManager.createQuery("SELECT m FROM Message m WHERE m.messageDetails.sender_Id=:senderId AND m.messageDetails.receiver_Id =:receiverId" +
                    " AND m.status = FALSE ORDER BY m.timestamp DESC", Message.class)
                    .setParameter("senderId", senderUserId)
                    .setParameter("receiverId", receiverUserIde)
                    .getResultList();
        } catch (Exception e) {
            log.error(e.getMessage());
            return messages;
        }
        return messages;
    }

    @Override
    public int countNewMessagesForUser(String senderName,String receiverName) {
         int count;
        try {
            count = ((Number) entityManager.createNativeQuery("SELECT COUNT(um.MESSAGE_ID) FROM USER_MESSAGES um join USERS u " +
                    "                        on RECEIVER_ID = u.ID join USERS u2 " +
                    "                            on SENDER_ID = u2.ID " +
                    "                    join MESSAGES m on um.MESSAGE_ID = m.ID " +
                    "                         WHERE (u.USERNAME =:username1 AND u2.USERNAME =:username2) " +
                    "                    AND m.status = FALSE")
                    .setParameter("username1", receiverName)
                    .setParameter("username2", senderName)
                    .getSingleResult()).intValue();
        } catch (Exception e) {
            log.error(e.getMessage());
            return 0;
        }
        return count;
    }

    @Override
    public Integer getMessagesCount(Long senderId, Long receiverId) {
        Long count;
        try {
            count = entityManager.createQuery("SELECT COUNT(me.messageIdg) FROM MessageDetails me WHERE (me.sender_Id = :userId1 OR " +
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
    public List<Message> getMessagesByLimit(Long userId1, Long userId2, int minLimit, int range) {
        List<Message> messages = new LinkedList<>();
        try {
            List<Tuple> data = entityManager.createNativeQuery("SELECT me.id,me.messageContent,me.TIMESTAMP,me.STATUS,u.USERNAME as senderUsername, " +
                    "     u2.USERNAME as receiverUsername, ud.PROFILEIMG as senderUserData," +
                    "     ud2.PROFILEIMG as receiverUserData " +
                    "FROM MESSAGES me " +
                    "    join USER_MESSAGES um " +
                    "        on me.ID = um.MESSAGE_ID " +
                    "    join USERS u " +
                    "        on u.ID = SENDER_ID " +
                    "    join USERS u2 " +
                    "        on u2.ID = RECEIVER_ID " +
                    "    join USERDATA ud " +
                    "        on ud.USERID = SENDER_ID " +
                    "    join USERDATA ud2 " +
                    "        on ud2.USERID = RECEIVER_ID " +
                    "            where " +
                    "(sender_Id =:userId1 OR receiver_Id =:userId1) " +
                    "              AND (sender_Id =:userId2 OR receiver_Id =:userId2) " +
                    "ORDER BY me.timestamp",Tuple.class)
                    .setParameter("userId1", userId1)
                    .setParameter("userId2", userId2)
                    .setFirstResult(minLimit)
                    .setMaxResults(range)
                    .getResultList();
            data.forEach((tuple) -> messages.add(
                    Message.builder()
                            .id(Long.valueOf((String.valueOf(tuple.get(0)))))
                            .messageContent(String.valueOf(tuple.get(1)))
                            .timestamp(String.valueOf(tuple.get(2)))
                            .status((Boolean) tuple.get(3))
                            .senderUsername(String.valueOf(tuple.get(4)))
                            .receiverUsername(String.valueOf(tuple.get(5)))
                            .senderUserData(String.valueOf(tuple.get(6)))
                            .receiverUserData(String.valueOf(tuple.get(7)))
                            .build()
            ));
        } catch (Exception e) {
            log.info(e.getMessage());
            return new LinkedList<>();
        }
        return messages;
    }

    @Override
    public boolean madeReadedMessages(List<Message> messages) {
        return true;
    }
}
