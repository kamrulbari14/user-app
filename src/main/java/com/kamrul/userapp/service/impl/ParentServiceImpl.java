package com.kamrul.userapp.service.impl;

import com.kamrul.userapp.dto.user.ParentDto;
import com.kamrul.userapp.entity.user.Parent;
import com.kamrul.userapp.enums.ActiveStatus;
import com.kamrul.userapp.exception.ResponseProviderException;
import com.kamrul.userapp.repository.ParentRepository;
import com.kamrul.userapp.service.ParentService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParentServiceImpl implements ParentService {

  private final ModelMapper modelMapper;
  private final ParentRepository repository;

  @Override
  public ParentDto saveParentUser(ParentDto parentDto) {
    modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

    Parent newParent = modelMapper.map(parentDto, Parent.class);
    newParent = repository.save(newParent);
    if (newParent.getId() == null) {
      throw new ResponseProviderException("Parent user can not be created", HttpStatus.BAD_REQUEST);
    }

    return modelMapper.map(newParent, ParentDto.class);
  }

  @Override
  public ParentDto getParentUserById(Long id) {
    Optional<Parent> result = repository.findByIdAndActiveStatus(id,
        ActiveStatus.ACTIVE.getValue());
    if (result.isEmpty()) {
      throw new ResponseProviderException("Parent user with ID = " + id + " is not found",
          HttpStatus.NOT_FOUND);
    }
    return modelMapper.map(result.get(), ParentDto.class);
  }

  @Override
  public List<ParentDto> getAllParentUserByActiveStatus(ActiveStatus status) {
    List<Parent> parentUserList = repository.findAllByActiveStatus(status.getValue());
    if (parentUserList.isEmpty()) {
      throw new ResponseProviderException("No patient found with status = " + status,
          HttpStatus.NOT_FOUND);
    }

    return toDtoList(parentUserList);
  }

  @Override
  public ParentDto updateParentUser(ParentDto parentDto, Long id) {
    if (parentDto == null) {
      throw new ResponseProviderException("There is nothing to update", HttpStatus.BAD_REQUEST);
    }

    Optional<Parent> result = repository.findByIdAndActiveStatus(id,
        ActiveStatus.ACTIVE.getValue());

    if (result.isEmpty()) {
      throw new ResponseProviderException("Parent user with ID = " + id + " is not found",
          HttpStatus.NOT_FOUND);
    }

    Parent existingUser = result.get();
    modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
    modelMapper.map(parentDto, existingUser);
    repository.save(existingUser);

    return modelMapper.map(existingUser, ParentDto.class);
  }

  @Override
  public Boolean deleteParentUser(Long id, Boolean permanentlyDelete) {
    Optional<Parent> result = repository.findByIdAndActiveStatus(id,
        ActiveStatus.ACTIVE.getValue());

    if (result.isEmpty()) {
      throw new ResponseProviderException("Parent user with ID = " + id + " is not found",
          HttpStatus.NOT_FOUND);
    }

    if (!permanentlyDelete) {
      Parent existingUser = result.get();
      existingUser.setActiveStatus(ActiveStatus.DELETE.getValue());
      existingUser = repository.save(existingUser);
      return existingUser.getActiveStatus() == ActiveStatus.DELETE.getValue();
    }
    repository.deleteById(id);
    return true;
  }

  private List<ParentDto> toDtoList(List<Parent> entityList) {
    return entityList.stream().map(entity -> modelMapper.map(entity, ParentDto.class))
        .collect(Collectors.toList());
  }
}
