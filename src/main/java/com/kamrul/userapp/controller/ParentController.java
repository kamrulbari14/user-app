package com.kamrul.userapp.controller;

import com.kamrul.userapp.annotation.ApiController;
import com.kamrul.userapp.dto.response.Response;
import com.kamrul.userapp.dto.user.ParentDto;
import com.kamrul.userapp.enums.ActiveStatus;
import com.kamrul.userapp.service.ParentService;
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
 * Controller class responsible for handling HTTP requests related to parent users. This class
 * provides endpoints for creating, retrieving, updating, and deleting parent users.
 */
@ApiController
@RequiredArgsConstructor
@RequestMapping("/api/v1/parents")
public class ParentController {

  private final ParentService parentService;

  /**
   * Endpoint to create a new parent user.
   *
   * @param parentDto The DTO object containing information about the parent user to be created.
   * @param result    The binding result for validation.
   * @return ResponseEntity containing the response to the client.
   */
  @PostMapping
  public ResponseEntity<Response<ParentDto>> saveParent(@Valid @RequestBody ParentDto parentDto,
      BindingResult result) {

    if (result.hasErrors()) {
      return ResponseEntity.badRequest()
          .body(ResponseBuilder.getFailureResponse(result, "There are some validation errors!!"));
    }

    Response<ParentDto> response = ResponseBuilder.getSuccessResponse(HttpStatus.CREATED,
        "Parent user created successfully", parentService.saveParentUser(parentDto), 1);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  /**
   * Endpoint to retrieve a parent user by ID.
   *
   * @param userId The ID of the parent user to retrieve.
   * @return ResponseEntity containing the response to the client.
   */
  @GetMapping("/{userId}")
  public ResponseEntity<Response<ParentDto>> getParentUserById(@PathVariable Long userId) {
    Response<ParentDto> response = ResponseBuilder.getSuccessResponse(HttpStatus.OK,
        "Parent user retrieved successfully", parentService.getParentUserById(userId), 1);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  /**
   * Endpoint to retrieve all parent users with optional filtering by status.
   *
   * @param status The active status of parent users to retrieve (optional).
   * @return ResponseEntity containing the response to the client.
   */
  @GetMapping
  public ResponseEntity<Response<List<ParentDto>>> getParentUserList(
      @RequestParam(required = false) ActiveStatus status) {
    if (status == null) {
      status = ActiveStatus.ACTIVE;
    }
    List<ParentDto> userList = parentService.getAllParentUserByActiveStatus(status);
    Response<List<ParentDto>> response = ResponseBuilder.getSuccessResponse(HttpStatus.OK,
        "Parent user list with status = " + status + " retrieved successfully", userList,
        userList.size());
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  /**
   * Endpoint to update an existing parent user.
   *
   * @param userId    The ID of the parent user to update.
   * @param parentDto The DTO object containing updated information about the parent user.
   * @return ResponseEntity containing the response to the client.
   */
  @PutMapping("/{userId}")
  public ResponseEntity<Response<ParentDto>> updateParentUser(@PathVariable Long userId,
      @RequestBody(required = false) ParentDto parentDto) {
    Response<ParentDto> response = ResponseBuilder.getSuccessResponse(HttpStatus.ACCEPTED,
        "Parent user updated successfully", parentService.updateParentUser(parentDto, userId), 1);
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
  }

  /**
   * Endpoint to delete a parent user by ID, with an option for permanent deletion.
   *
   * @param userId    The ID of the parent user to delete.
   * @param permanent Flag indicating whether to permanently delete the parent user (optional,
   *                  default is false).
   * @return ResponseEntity containing the response to the client.
   */
  @DeleteMapping("/{userId}")
  public ResponseEntity<Response<String>> deleteParentUser(@PathVariable Long userId,
      @RequestParam(required = false) Boolean permanent) {

    if (permanent == null) {
      permanent = false;
    }

    if (parentService.deleteParentUser(userId, permanent)) {
      Response<String> response = ResponseBuilder.getResponseOnlyWithMessage(HttpStatus.OK,
          permanent ? "Parent user deleted permanently" : "Parent user deleted successfully");
      return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    Response<String> response = ResponseBuilder.getResponseOnlyWithMessage(HttpStatus.BAD_REQUEST,
        "Could not delete parent user with ID = " + userId);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

}
