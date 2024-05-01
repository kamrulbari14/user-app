package com.kamrul.userapp.entity.user;

import com.kamrul.userapp.entity.BaseUserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class representing a child user.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Child extends BaseUserEntity {

  /**
   * The parent associated with the child.
   */
  @ManyToOne
  private Parent parent;

  @Builder(builderMethodName = "childBuilder")
  public Child(Long id, String firstName, String lastName, Parent parent) {
    super(id, firstName, lastName);
    this.parent = parent;
  }
}
