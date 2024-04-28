package com.kamrul.userapp.dto.user;

import com.kamrul.userapp.dto.BaseUserDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChildDto extends BaseUserDto {

  private ParentDto parent;
}
