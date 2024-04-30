package com.kamrul.userapp.entity;

import com.kamrul.userapp.enums.ActiveStatus;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Base entity class representing common attributes for user entities.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class BaseUserEntity implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * The unique identifier for the entity.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * The first name of the user.
   */
  private String firstName;

  /**
   * The last name of the user.
   */
  private String lastName;

  /**
   * The date and time when the entity was created.
   */
  @Column(updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime createdAt;

  /**
   * The date and time when the entity was last updated.
   */
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime updateAt;

  /**
   * The active status of the entity.
   */
  private Integer activeStatus;

  /**
   * Sets data before inserting the entity into the database.
   */
  @PrePersist
  public void setPreInsertData() {
    this.createdAt = LocalDateTime.now();
    if (this.activeStatus == null) {
      this.activeStatus = ActiveStatus.ACTIVE.getValue();
    }
  }

  /**
   * Sets data before updating the entity in the database.
   */
  @PreUpdate
  public void setPreUpdateData() {
    this.updateAt = LocalDateTime.now();
  }
}
