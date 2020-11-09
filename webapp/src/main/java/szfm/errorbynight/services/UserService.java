package szfm.errorbynight.services;

import szfm.errorbynight.model.User;
import szfm.errorbynight.model.UserData;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {

    void registerUser(User user) throws SQLException;

    Optional<User> findByUsername(String username);

    boolean userActivation(String code);

//    boolean saveUserData(UserData userData);
//
//    boolean sendMessage(String usernameTo, User userFrom, String message);
//     Map<String,Integer> getConversationUsernamesAndNewMessageCount(User user,int range,int lowerLimit);
//    List<String> getConversationUsernames(User user, int lowerLimit, int range);
//     List<Message> getMessages(Long userId1, Long userId2, int lowerLimit, int range);
//
//    Integer getMessagesCount(Long senderId, Long receiverId);
//
//    Map<Message, Integer> getNewMessagesAndPlace(Long senderId, Long receiverId);
//
//    List<Message> getNewMessages(Long senderId, Long receiverId);
//
//    boolean readMessages(List<Message> messages);
//
//    Long getUserIdByName(String username);
}
