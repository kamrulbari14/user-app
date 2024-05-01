package com.kamrul.userapp.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Base DTO class representing common attributes for user DTOs.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BaseUserDto {

  private Long id;

  @NotBlank(message = "First name is required")
  private String firstName;

  @NotBlank(message = "Last name is required")
  private String lastName;

  private LocalDateTime createdAt;
  private LocalDateTime updateAt;

  private Integer activeStatus;

  public BaseUserDto(Long id, String firstName, String lastName) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
  }
}
