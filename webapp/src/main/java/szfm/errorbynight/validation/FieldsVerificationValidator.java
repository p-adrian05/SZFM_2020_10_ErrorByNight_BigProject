package szfm.errorbynight.validation;

import javax.validation.ConstraintValidator;

public class FieldsVerificationValidator implements ConstraintValidator<FieldsVerification,Object> {
  private String field;
  private String fieldMatch;

  @Override
    public void initialize(FieldsVerification constraintAnnotation) {
        field = constraintAnnotation.field();
        fieldMatch = constraintAnnotation.fieldMatch();
    }
}
