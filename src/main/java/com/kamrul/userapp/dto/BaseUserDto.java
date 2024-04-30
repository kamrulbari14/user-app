package com.kamrul.userapp.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * Base DTO class representing common attributes for user DTOs.
 */
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

}
