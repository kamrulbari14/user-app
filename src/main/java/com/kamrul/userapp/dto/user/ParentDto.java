package com.kamrul.userapp.dto.user;

import com.kamrul.userapp.dto.BaseUserDto;
import com.kamrul.userapp.dto.address.AddressDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO class representing a parent user.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ParentDto extends BaseUserDto {

  @NotNull(message = "Please provide an address")
  private AddressDto address;

  @Builder(builderMethodName = "parentBuilder")
  public ParentDto(Long id, String firstName, String lastName, AddressDto address) {
    super(id, firstName, lastName);
    this.address = address;
  }
}
