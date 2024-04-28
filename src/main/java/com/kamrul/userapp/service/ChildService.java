package com.kamrul.userapp.service;

import com.kamrul.userapp.dto.user.ChildDto;
import com.kamrul.userapp.enums.ActiveStatus;
import java.util.List;

public interface ChildService {

  ChildDto saveChildUser(ChildDto childDto);

  ChildDto getChildUserById(Long id);

  List<ChildDto> getAllChildUserByActiveStatus(ActiveStatus status);

  ChildDto updateChildUser(ChildDto childDto, Long id);

  Boolean deleteChildUser(Long id, Boolean permanentlyDelete);

}
