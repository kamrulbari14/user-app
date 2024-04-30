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

/**
 * Implementation of the ParentService interface that provides functionality to manage parent users.
 * This service class handles operations such as saving, retrieving, updating, and deleting parent users.
 */
@Service
@RequiredArgsConstructor
public class ParentServiceImpl implements ParentService {

  private final ModelMapper modelMapper;
  private final ParentRepository repository;

  /**
   * Saves a new parent user based on the provided ParentDto.
   *
   * @param parentDto The DTO object containing information about the parent user to be saved.
   * @return The DTO representation of the saved parent user.
   * @throws ResponseProviderException If the parent user cannot be created.
   */
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

  /**
   * Retrieves a parent user by its ID.
   *
   * @param id The ID of the parent user to retrieve.
   * @return The DTO representation of the retrieved parent user.
   * @throws ResponseProviderException If the parent user with the specified ID is not found.
   */
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

  /**
   * Retrieves all parent users with the specified active status.
   *
   * @param status The active status of the parent users to retrieve.
   * @return A list of DTO representations of the retrieved parent users.
   * @throws ResponseProviderException If no parent users are found with the specified status.
   */
  @Override
  public List<ParentDto> getAllParentUserByActiveStatus(ActiveStatus status) {
    List<Parent> parentUserList = repository.findAllByActiveStatus(status.getValue());
    if (parentUserList.isEmpty()) {
      throw new ResponseProviderException("No patient found with status = " + status,
          HttpStatus.NOT_FOUND);
    }

    return toDtoList(parentUserList);
  }

  /**
   * Updates an existing parent user with the provided information.
   *
   * @param parentDto The DTO object containing updated information about the parent user.
   * @param id        The ID of the parent user to update.
   * @return The DTO representation of the updated parent user.
   * @throws ResponseProviderException If there is nothing to update or if the parent user with the specified ID is not found.
   */
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

  /**
   * Deletes a parent user by ID, either permanently or by setting its active status to DELETE.
   *
   * @param id                The ID of the parent user to delete.
   * @param permanentlyDelete A flag indicating whether to permanently delete the parent user.
   * @return True if the parent user is successfully deleted.
   * @throws ResponseProviderException If the parent user with the specified ID is not found.
   */
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

  /**
   * Converts a list of Parent entities to a list of ParentDto objects.
   *
   * @param entityList The list of Parent entities to convert.
   * @return A list of DTO representations of the Parent entities.
   */
  private List<ParentDto> toDtoList(List<Parent> entityList) {
    return entityList.stream().map(entity -> modelMapper.map(entity, ParentDto.class))
        .collect(Collectors.toList());
  }
}
