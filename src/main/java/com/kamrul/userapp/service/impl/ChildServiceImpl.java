package com.kamrul.userapp.service.impl;

import com.kamrul.userapp.dto.user.ChildDto;
import com.kamrul.userapp.entity.user.Child;
import com.kamrul.userapp.entity.user.Parent;
import com.kamrul.userapp.enums.ActiveStatus;
import com.kamrul.userapp.exception.ResponseProviderException;
import com.kamrul.userapp.repository.ChildRepository;
import com.kamrul.userapp.service.ChildService;
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
public class ChildServiceImpl implements ChildService {

  private final ModelMapper modelMapper;
  private final ParentService parentService;
  private final ChildRepository repository;

  /**
   * Saves a new child user based on the provided ChildDto.
   *
   * @param childDto The DTO object containing information about the child user to be saved.
   * @return The DTO representation of the saved child user.
   * @throws ResponseProviderException If the child user cannot be created.
   * @author Kamrul Bari
   */
  @Override
  public ChildDto saveChildUser(ChildDto childDto) {
    modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

    Child newChild = modelMapper.map(childDto, Child.class);
    newChild.setParent(
        modelMapper.map(parentService.getParentUserById(childDto.getParent().getId()),
            Parent.class));
    newChild = repository.save(newChild);
    if (newChild.getId() == null) {
      throw new ResponseProviderException("Child user can not be created", HttpStatus.BAD_REQUEST);
    }

    return modelMapper.map(newChild, ChildDto.class);
  }

  /**
   * Retrieves a child user by its ID.
   *
   * @param id The ID of the child user to retrieve.
   * @return The DTO representation of the retrieved child user.
   * @throws ResponseProviderException If the child user with the specified ID is not found.
   * @author Kamrul Bari
   */
  @Override
  public ChildDto getChildUserById(Long id) {
    Optional<Child> result = repository.findByIdAndActiveStatus(id, ActiveStatus.ACTIVE.getValue());
    if (result.isEmpty()) {
      throw new ResponseProviderException("Child user with ID = " + id + " is not found",
          HttpStatus.NOT_FOUND);
    }
    return modelMapper.map(result.get(), ChildDto.class);
  }

  /**
   * Retrieves all child users with the specified active status.
   *
   * @param status The active status of the child users to retrieve.
   * @return A list of DTO representations of the retrieved child users.
   * @throws ResponseProviderException If no child users are found with the specified status.
   * @author Kamrul Bari
   */
  @Override
  public List<ChildDto> getAllChildUserByActiveStatus(ActiveStatus status) {
    List<Child> childUserList = repository.findAllByActiveStatus(status.getValue());
    if (childUserList.isEmpty()) {
      throw new ResponseProviderException("No patient found with status = " + status,
          HttpStatus.NOT_FOUND);
    }

    return toDtoList(childUserList);
  }

  /**
   * Updates an existing child user with the provided information.
   *
   * @param childDto The DTO object containing updated information about the child user.
   * @param id       The ID of the child user to update.
   * @return The DTO representation of the updated child user.
   * @throws ResponseProviderException If there is nothing to update or if the child user with the
   *                                   specified ID is not found.
   * @author Kamrul Bari
   */
  @Override
  public ChildDto updateChildUser(ChildDto childDto, Long id) {
    if (childDto == null) {
      throw new ResponseProviderException("There is nothing to update", HttpStatus.BAD_REQUEST);
    }

    Optional<Child> result = repository.findByIdAndActiveStatus(id, ActiveStatus.ACTIVE.getValue());

    if (result.isEmpty()) {
      throw new ResponseProviderException("Child user with ID = " + id + " is not found",
          HttpStatus.NOT_FOUND);
    }

    Child existingUser = result.get();
    modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
    modelMapper.map(childDto, existingUser);
    repository.save(existingUser);

    return modelMapper.map(existingUser, ChildDto.class);
  }

  /**
   * Deletes a child user by ID, either permanently or by setting its active status to DELETE.
   *
   * @param id                The ID of the child user to delete.
   * @param permanentlyDelete A flag indicating whether to permanently delete the child user.
   * @return True if the child user is successfully deleted.
   * @throws ResponseProviderException If the child user with the specified ID is not found.
   * @author Kamrul Bari
   */
  @Override
  public Boolean deleteChildUser(Long id, Boolean permanentlyDelete) {
    Optional<Child> result = repository.findByIdAndActiveStatus(id, ActiveStatus.ACTIVE.getValue());

    if (result.isEmpty()) {
      throw new ResponseProviderException("Child user with ID = " + id + " is not found",
          HttpStatus.NOT_FOUND);
    }

    if (!permanentlyDelete) {
      Child existingUser = result.get();
      existingUser.setActiveStatus(ActiveStatus.DELETE.getValue());
      existingUser = repository.save(existingUser);
      return existingUser.getActiveStatus() == ActiveStatus.DELETE.getValue();
    }
    repository.deleteById(id);
    return true;
  }

  /**
   * Converts a list of Child entities to a list of ChildDto objects.
   *
   * @param entityList The list of Child entities to convert.
   * @return A list of DTO representations of the Child entities.
   * @author Kamrul Bari
   */
  private List<ChildDto> toDtoList(List<Child> entityList) {
    return entityList.stream().map(entity -> modelMapper.map(entity, ChildDto.class))
        .collect(Collectors.toList());
  }
}
