package szfm.errorbynight.validation;

import javax.validation.Constraint;
import java.lang.annotation.*;
import javax.validation.Payload;

@Constraint(validatedBy = FieldsVerificationValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface FieldsVerification {

  String message() default "Fields do not match";
  String field();
  String fieldMatch();

  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};

  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.TYPE)
  @interface List{
    FieldsVerification[] value();
  }
}
