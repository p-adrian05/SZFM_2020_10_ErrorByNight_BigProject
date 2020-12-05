package szfm.errorbynight.services;

public interface EmailService {

     boolean sendMessage(String email,String username,String activationCode);

}
