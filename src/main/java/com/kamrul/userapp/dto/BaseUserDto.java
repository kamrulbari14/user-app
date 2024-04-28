package com.kamrul.userapp.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseUserDto {

  private Long id;

  private String firstName;
  private String lastName;

  private LocalDateTime createdAt;
  private LocalDateTime updateAt;

  private Integer activeStatus;

}
