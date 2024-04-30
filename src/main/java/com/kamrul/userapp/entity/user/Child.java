package com.kamrul.userapp.entity.user;

import com.kamrul.userapp.entity.BaseUserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity class representing a child user.
 */
@Getter
@Setter
@Entity
public class Child extends BaseUserEntity {

  /**
   * The parent associated with the child.
   */
  @ManyToOne
  private Parent parent;
}
