package szfm.errorbynight.validation;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Constraint(validatedBy = FieldsVerificationValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface FieldsVerification {

}
