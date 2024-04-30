package com.kamrul.userapp.util.validator;

import com.kamrul.userapp.annotation.validator.IsValidParent;
import com.kamrul.userapp.dto.user.ParentDto;
import com.kamrul.userapp.enums.ActiveStatus;
import com.kamrul.userapp.repository.ParentRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Validator class responsible for validating parent entities. This class implements the
 * ConstraintValidator interface for the IsValidParent annotation.
 */
@RequiredArgsConstructor
@Component
public class ParentValidator implements ConstraintValidator<IsValidParent, ParentDto> {

  private final ParentRepository parentRepository;

  /**
   * Initializes the validator.
   *
   * @param constraintAnnotation The annotation instance.
   */
  @Override
  public void initialize(IsValidParent constraintAnnotation) {
  }

  /**
   * Validates whether the specified parent entity exists.
   *
   * @param parentDto The parent DTO to validate.
   * @param context   The validation context.
   * @return True if the parent exists, false otherwise.
   */
  @Override
  public boolean isValid(ParentDto parentDto, ConstraintValidatorContext context) {
    if (parentDto == null) {
      return true; // Let other validators handle null values
    }
    return parentRepository.findByIdAndActiveStatus(parentDto.getId(),
        ActiveStatus.ACTIVE.getValue()).isPresent();
  }
}
