package com.kamrul.userapp.entity.user;

import com.kamrul.userapp.entity.BaseUserEntity;
import com.kamrul.userapp.entity.address.Address;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity class representing a parent user.
 */
@Getter
@Setter
@Entity
public class Parent extends BaseUserEntity {

  /**
   * The address associated with the parent.
   */
  @OneToOne(cascade = CascadeType.ALL)
  private Address address;
}
