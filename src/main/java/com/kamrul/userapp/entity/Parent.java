package com.kamrul.userapp.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Parent extends BaseUserEntity{

  @OneToOne(cascade = CascadeType.ALL)
  private Address address;
}
