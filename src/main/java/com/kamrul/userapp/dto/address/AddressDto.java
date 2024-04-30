package com.kamrul.userapp.dto.address;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO class representing an address.
 */
@Getter
@Setter
public class AddressDto {

  private Long id;
  private String street;
  private String city;
  private String state;
  private String zip;

}
