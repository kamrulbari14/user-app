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

@ApiController
@RequiredArgsConstructor
@RequestMapping("/api/v1/parents")
public class ParentController {

  private final ParentService parentService;

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

  @GetMapping("/{userId}")
  public ResponseEntity<Response<ParentDto>> getParentUserById(@PathVariable Long userId) {
    Response<ParentDto> response = ResponseBuilder.getSuccessResponse(HttpStatus.OK,
        "Parent user retrieved successfully", parentService.getParentUserById(userId), 1);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @GetMapping
  public ResponseEntity<Response<List<ParentDto>>> getParentUserById(
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

  @PutMapping("/{userId}")
  public ResponseEntity<Response<ParentDto>> updateParentUser(@PathVariable Long userId,
      @RequestBody(required = false) ParentDto parentDto) {
    Response<ParentDto> response = ResponseBuilder.getSuccessResponse(HttpStatus.ACCEPTED,
        "Parent user updated successfully", parentService.updateParentUser(parentDto, userId), 1);
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
  }

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
