package szfm.errorbynight.validation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import szfm.errorbynight.validation.FieldsVerification;

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
}
