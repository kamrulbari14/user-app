package com.kamrul.userapp.entity.user;

import com.kamrul.userapp.entity.BaseUserEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Child extends BaseUserEntity {

  @OneToOne(cascade = CascadeType.ALL)
  private Parent parent;
}
