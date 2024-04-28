package com.kamrul.userapp.service.impl;

import com.kamrul.userapp.dto.user.ChildDto;
import com.kamrul.userapp.enums.ActiveStatus;
import com.kamrul.userapp.service.ChildService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ChildServiceImpl implements ChildService {

  @Override
  public ChildDto saveChildUser(ChildDto childDto) {
    return null;
  }

  @Override
  public ChildDto getChildUserById(Long id) {
    return null;
  }

  @Override
  public List<ChildDto> getAllChildUserByActiveStatus(ActiveStatus status) {
    return null;
  }

  @Override
  public ChildDto updateChildUser(ChildDto childDto, Long id) {
    return null;
  }

  @Override
  public Boolean deleteChildUser(Long id, Boolean permanentlyDelete) {
    return null;
  }
}
