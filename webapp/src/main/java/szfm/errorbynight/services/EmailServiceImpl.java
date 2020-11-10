package szfm.errorbynight.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import szfm.errorbynight.util.Mappings;

@Slf4j
@Service
@PropertySources(value = {
        @PropertySource("classpath:email.properties"),
        @PropertySource("classpath:application.properties")
})
public class EmailServiceImpl implements EmailService{

    @Autowired
    private Environment env;

    private JavaMailSender javaMailSender;

    @Autowired
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendMessage(String email,String username,String activationCode) {
        SimpleMailMessage mailMessage;
        try{
            mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(env.getProperty("mail.username"));
            mailMessage.setTo(email);
            mailMessage.setSubject("Successful registration.");
            mailMessage.setText("Dear " + username+ "\n Thanks for registering!"+
                    "\n Click here for the activation: "
                    +env.getProperty("host.name")+"/"+ Mappings.REGISTRATION+"/"+Mappings.ACTIVATION+"/"+activationCode);
            javaMailSender.send(mailMessage);

        }catch (Exception e){
            log.error("Error to sent email: "+email+" "+e.getMessage());
        }
    }
}
