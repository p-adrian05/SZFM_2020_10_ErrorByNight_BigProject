package szfm.errorbynight.services;

public interface EmailService {

    void sendMessage(String email,String username,String activationCode);

}
