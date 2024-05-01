package com.kamrul.userapp.entity.user;

import com.kamrul.userapp.entity.BaseUserEntity;
import com.kamrul.userapp.entity.address.Address;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class representing a parent user.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Parent extends BaseUserEntity {

  /**
   * The address associated with the parent.
   */
  @OneToOne(cascade = CascadeType.ALL)
  private Address address;

  @Builder(builderMethodName = "parentBuilder")
  public Parent(Long id, String firstName, String lastName, Address address) {
    super(id, firstName, lastName);
    this.address = address;
  }
}
