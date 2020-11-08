package szfm.errorbynight.repository;

import szfm.errorbynight.model.User;
import szfm.errorbynight.model.UserData;

import java.util.List;
import java.util.Optional;

/**
 * DAO interface for the {@link User} entity.
 */
public interface UserDao extends GenericDao<User> {

    Optional<User> findByUsername(String username);

    Optional<User> findByActivation(String code);

    void saveUserData(UserData entity);

    //void sendMessage(Message message);

    Optional<Long> getIdByUsername(String username);

    List<String> getConversationUsernames(User user, int lowerLimit, int range);

     int countNewMessagesForUser(String username1, String username2);

    Integer getMessagesCount(Long senderUserId, Long receiverUserId);

    //List<Message> getNewMessages(Long senderUserId, Long receiverUserIde);

    //List<Message> getMessagesByLimit(Long userId1, Long userId2, int minLimit, int maxLimit);

    Optional<Long> getIdByEmail(String email);

    //boolean madeReadedMessages(List<Message> messages);
}
