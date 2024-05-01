package com.kamrul.userapp.dto.user;

import com.kamrul.userapp.annotation.validator.IsValidParent;
import com.kamrul.userapp.dto.BaseUserDto;
import com.kamrul.userapp.dto.address.AddressDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO class representing a child user.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChildDto extends BaseUserDto {

  @NotNull(message = "Parent is required to create a child")
  @IsValidParent
  private ParentDto parent;

  @Builder(builderMethodName = "childBuilder")
  public ChildDto(Long id, String firstName, String lastName, ParentDto parent) {
    super(id, firstName, lastName);
    this.parent = parent;
  }
}
