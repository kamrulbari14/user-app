package com.kamrul.userapp.service;

import com.kamrul.userapp.dto.user.ParentDto;
import com.kamrul.userapp.enums.ActiveStatus;
import java.util.List;

public interface ParentService {

  ParentDto saveParentUser(ParentDto parentDto);

  ParentDto getParentUserById(Long id);

  List<ParentDto> getAllParentUserByActiveStatus(ActiveStatus status);

  ParentDto updateParentUser(ParentDto parentDto, Long id);

  Boolean deleteParentUser(Long id, Boolean permanentlyDelete);

}
