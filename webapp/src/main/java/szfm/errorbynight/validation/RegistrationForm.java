package szfm.errorbynight.validation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import szfm.errorbynight.validation.FieldsVerification;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@FieldsVerification.List( value = {
        @FieldsVerification(
                field = "password",
                fieldMatch = "verifyPassword",
                message = "Passwords do not match"
        )
})
public class RegistrationForm {
  @Size(min = 2,max = 15,message = "Username: min 2 characters required, max 10 characters allowed")
  @NotEmpty(message = "Username cannot be empty")
  private String username;

  @Email(message = "Email is invalid")
  @NotEmpty(message = "Email cannot be empty")
  private String email;

  @NotEmpty(message = "Cannot be empty")
  @Size(min = 8,message = "min 8 characters required")
  @Pattern(regexp = "(?=.*[0-9]).{8,}",message = "a digit must occur at least once")
  @Pattern(regexp = "(?=.*[a-z]).{8,}",message = "a lower case letter must occur at least once")
  @Pattern(regexp = "(?=.*[A-Z]).{8,}",message = "an upper case letter must occur at least once")
  @Pattern(regexp = "(?=.*[@#$%^&+=]).{8,}",message = "a special character must occur at least once")
  @Pattern(regexp = "(?=\\S+$).{8,}",message = "no whitespace allowed")
  private String password;

  private String verifyPassword;
}
