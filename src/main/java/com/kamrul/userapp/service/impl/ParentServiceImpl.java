package com.kamrul.userapp.service.impl;

import com.kamrul.userapp.dto.user.ParentDto;
import com.kamrul.userapp.enums.ActiveStatus;
import com.kamrul.userapp.service.ParentService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ParentServiceImpl implements ParentService {

  @Override
  public ParentDto saveParentUser(ParentDto parentDto) {
    return null;
  }

  @Override
  public ParentDto getParentUserById(Long id) {
    return null;
  }

  @Override
  public List<ParentDto> getAllParentUserByActiveStatus(ActiveStatus status) {
    return null;
  }

  @Override
  public ParentDto updateParentUser(ParentDto parentDto, Long id) {
    return null;
  }

  @Override
  public Boolean deleteParentUser(Long id, Boolean permanentlyDelete) {
    return null;
  }
}
