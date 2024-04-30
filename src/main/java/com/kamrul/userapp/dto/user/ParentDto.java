package com.kamrul.userapp.dto.user;

import com.kamrul.userapp.dto.address.AddressDto;
import com.kamrul.userapp.dto.BaseUserDto;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParentDto extends BaseUserDto {

  @NotNull(message = "Please provide an address")
  private AddressDto address;
}
