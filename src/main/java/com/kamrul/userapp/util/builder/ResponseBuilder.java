package com.kamrul.userapp.util.builder;

import com.kamrul.userapp.dto.response.ErrorResponseDto;
import com.kamrul.userapp.dto.response.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

public final class ResponseBuilder {

  private static List<ErrorResponseDto> getCustomError(BindingResult result) {
    List<ErrorResponseDto> dtoList = new ArrayList<>();
    result.getFieldErrors().forEach(fieldError -> {
      ErrorResponseDto errorResponseDto = ErrorResponseDto.builder().field(fieldError.getField())
          .message(fieldError.getDefaultMessage()).build();
      dtoList.add(errorResponseDto);
    });
    return dtoList;
  }

  public static <T> Response<T> getFailureResponse(BindingResult result, String message) {
    return Response.<T>builder().message(message).errors(getCustomError(result))
        .status(HttpStatus.BAD_REQUEST.getReasonPhrase()).statusCode(HttpStatus.BAD_REQUEST.value())
        .timeStamp(new Date().getTime()).build();
  }

  public static <T> Response<T> getFailureResponse(HttpStatus status, String message) {
    return Response.<T>builder().message(message).status(status.getReasonPhrase())
        .status(status.getReasonPhrase()).statusCode(status.value()).timeStamp(new Date().getTime())
        .build();
  }

  public static <T> Response<T> getSuccessResponse(HttpStatus status, String message, T content) {
    return Response.<T>builder().message(message).status(status.getReasonPhrase())
        .statusCode(status.value()).content(content).timeStamp(new Date().getTime()).build();
  }

  public static <T> Response<T> getSuccessResponse(HttpStatus status, String message, T content,
      int numberOfElement) {
    return Response.<T>builder().message(message).status(status.getReasonPhrase())
        .statusCode(status.value()).content(content).timeStamp(new Date().getTime())
        .numberOfElement(numberOfElement).build();
  }

}
