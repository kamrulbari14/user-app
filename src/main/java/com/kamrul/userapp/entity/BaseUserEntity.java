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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class BaseUserEntity implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String firstName;

  private String lastName;

  @Column(updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime createdAt;

  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime updateAt;
  private Integer activeStatus;

  @PrePersist
  public void setPreInsertData() {
    this.createdAt = LocalDateTime.now();
    if (this.activeStatus == null) {
      this.activeStatus = ActiveStatus.ACTIVE.getValue();
    }
  }

  @PreUpdate
  public void setPreUpdateData() {
    this.updateAt = LocalDateTime.now();
  }
}
