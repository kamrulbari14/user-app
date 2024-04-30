package com.kamrul.userapp.dto.user;

import com.kamrul.userapp.annotation.validator.IsValidParent;
import com.kamrul.userapp.dto.BaseUserDto;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO class representing a child user.
 */
@Getter
@Setter
public class ChildDto extends BaseUserDto {

  @NotNull(message = "Parent is required to create a child")
  @IsValidParent
  private ParentDto parent;
}
