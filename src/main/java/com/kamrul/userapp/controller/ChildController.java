package com.kamrul.userapp.controller;

import com.kamrul.userapp.annotation.ApiController;
import com.kamrul.userapp.dto.response.Response;
import com.kamrul.userapp.dto.user.ChildDto;
import com.kamrul.userapp.enums.ActiveStatus;
import com.kamrul.userapp.service.ChildService;
import com.kamrul.userapp.util.builder.ResponseBuilder;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller class responsible for handling HTTP requests related to child users. This class
 * provides endpoints for creating, retrieving, updating, and deleting child users.
 */
@ApiController
@RequiredArgsConstructor
@RequestMapping("/api/v1/child")
public class ChildController {

  private final ChildService childService;

  /**
   * Endpoint to create a new child user.
   *
   * @param childDto The DTO object containing information about the child user to be created.
   * @param result   The binding result for validation.
   * @return ResponseEntity containing the response to the client.
   */
  @PostMapping
  public ResponseEntity<Response<ChildDto>> saveChild(@Valid @RequestBody ChildDto childDto,
      BindingResult result) {

    if (result.hasErrors()) {
      return ResponseEntity.badRequest()
          .body(ResponseBuilder.getFailureResponse(result, "There are some validation errors!!"));
    }

    Response<ChildDto> response = ResponseBuilder.getSuccessResponse(HttpStatus.CREATED,
        "Child user created successfully", childService.saveChildUser(childDto), 1);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  /**
   * Endpoint to retrieve a child user by ID.
   *
   * @param userId The ID of the child user to retrieve.
   * @return ResponseEntity containing the response to the client.
   */
  @GetMapping("/{userId}")
  public ResponseEntity<Response<ChildDto>> getChildUserById(@PathVariable Long userId) {
    Response<ChildDto> response = ResponseBuilder.getSuccessResponse(HttpStatus.OK,
        "Child user retrieved successfully", childService.getChildUserById(userId), 1);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  /**
   * Endpoint to retrieve all child users with optional filtering by status.
   *
   * @param status The active status of child users to retrieve (optional).
   * @return ResponseEntity containing the response to the client.
   */
  @GetMapping
  public ResponseEntity<Response<List<ChildDto>>> getChildUserList(
      @RequestParam(required = false) ActiveStatus status) {
    if (status == null) {
      status = ActiveStatus.ACTIVE;
    }
    List<ChildDto> userList = childService.getAllChildUserByActiveStatus(status);
    Response<List<ChildDto>> response = ResponseBuilder.getSuccessResponse(HttpStatus.OK,
        "Child user list with status = " + status + " retrieved successfully", userList,
        userList.size());
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  /**
   * Endpoint to update an existing child user.
   *
   * @param userId   The ID of the child user to update.
   * @param childDto The DTO object containing updated information about the child user.
   * @return ResponseEntity containing the response to the client.
   */
  @PutMapping("/{userId}")
  public ResponseEntity<Response<ChildDto>> updateChildUser(@PathVariable Long userId,
      @RequestBody(required = false) ChildDto childDto) {
    Response<ChildDto> response = ResponseBuilder.getSuccessResponse(HttpStatus.ACCEPTED,
        "Child user updated successfully", childService.updateChildUser(childDto, userId), 1);
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
  }

  /**
   * Endpoint to delete a child user by ID, with an option for permanent deletion.
   *
   * @param userId    The ID of the child user to delete.
   * @param permanent Flag indicating whether to permanently delete the child user (optional,
   *                  default is false).
   * @return ResponseEntity containing the response to the client.
   */
  @DeleteMapping("/{userId}")
  public ResponseEntity<Response<String>> deleteChildUser(@PathVariable Long userId,
      @RequestParam(required = false) Boolean permanent) {

    if (permanent == null) {
      permanent = false;
    }

    if (childService.deleteChildUser(userId, permanent)) {
      Response<String> response = ResponseBuilder.getResponseOnlyWithMessage(HttpStatus.OK,
          permanent ? "Child user deleted permanently" : "Child user deleted successfully");
      return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    Response<String> response = ResponseBuilder.getResponseOnlyWithMessage(HttpStatus.BAD_REQUEST,
        "Could not delete child user with ID = " + userId);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

}
