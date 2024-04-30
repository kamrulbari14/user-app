package com.kamrul.userapp.annotation.validator;

import com.kamrul.userapp.util.validator.ParentValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom validation annotation used to validate whether a parent exists. This annotation is applied
 * to fields representing parent entities and is validated using the ParentValidator class.
 */
@Documented
@Constraint(validatedBy = ParentValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsValidParent {

  /**
   * Specifies the error message in case of validation failure.
   *
   * @return The error message.
   */
  String message() default "Parent cannot be found";

  /**
   * Specifies the validation groups to which this constraint belongs.
   *
   * @return The validation groups.
   */
  Class<?>[] groups() default {};

  /**
   * Specifies additional payload information.
   *
   * @return The payload information.
   */
  Class<? extends Payload>[] payload() default {};
}
