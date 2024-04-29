package com.kamrul.userapp.util.validator;

import com.kamrul.userapp.annotation.validator.IsValidParent;
import com.kamrul.userapp.dto.user.ParentDto;
import com.kamrul.userapp.enums.ActiveStatus;
import com.kamrul.userapp.repository.ParentRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ParentValidator implements ConstraintValidator<IsValidParent, ParentDto> {

  private final ParentRepository parentRepository;

  @Override
  public void initialize(IsValidParent constraintAnnotation) {
  }

  @Override
  public boolean isValid(ParentDto parentDto, ConstraintValidatorContext context) {
    if (parentDto == null) {
      return true; // Let other validators handle null values
    }
    return parentRepository.findByIdAndActiveStatus(parentDto.getId(),
        ActiveStatus.ACTIVE.getValue()).isPresent();
  }
}
