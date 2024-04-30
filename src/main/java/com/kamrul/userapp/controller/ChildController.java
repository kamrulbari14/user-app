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

@ApiController
@RequiredArgsConstructor
@RequestMapping("/api/v1/child")
public class ChildController {

  private final ChildService childService;

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

  @GetMapping("/{userId}")
  public ResponseEntity<Response<ChildDto>> getChildUserById(@PathVariable Long userId) {
    Response<ChildDto> response = ResponseBuilder.getSuccessResponse(HttpStatus.OK,
        "Child user retrieved successfully", childService.getChildUserById(userId), 1);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @GetMapping
  public ResponseEntity<Response<List<ChildDto>>> getChildUserById(
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

  @PutMapping("/{userId}")
  public ResponseEntity<Response<ChildDto>> updateChildUser(@PathVariable Long userId,
      @RequestBody(required = false) ChildDto childDto) {
    Response<ChildDto> response = ResponseBuilder.getSuccessResponse(HttpStatus.ACCEPTED,
        "Child user updated successfully", childService.updateChildUser(childDto, userId), 1);
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
  }

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
