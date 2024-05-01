package com.kamrul.userapp.dto.address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO class representing an address.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddressDto {

  private Long id;
  private String street;
  private String city;
  private String state;
  private String zip;

}
