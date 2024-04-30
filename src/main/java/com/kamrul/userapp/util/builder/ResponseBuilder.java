package com.kamrul.userapp.util.builder;

import com.kamrul.userapp.dto.response.ErrorResponseDto;
import com.kamrul.userapp.dto.response.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

/**
 * Utility class for building response objects.
 */
public final class ResponseBuilder {

  /**
   * Private constructor to prevent instantiation of the class.
   */
  private ResponseBuilder() {
    // Private constructor to prevent instantiation
  }

  /**
   * Generates a failure response based on the provided BindingResult and message.
   *
   * @param result  The binding result containing validation errors.
   * @param message The message to include in the response.
   * @param <T>     The type of the response content.
   * @return The constructed failure response.
   */
  public static <T> Response<T> getFailureResponse(BindingResult result, String message) {
    List<ErrorResponseDto> dtoList = getCustomError(result);
    return Response.<T>builder().message(message).errors(dtoList)
        .status(HttpStatus.BAD_REQUEST.getReasonPhrase()).statusCode(HttpStatus.BAD_REQUEST.value())
        .timeStamp(new Date().getTime()).build();
  }

  /**
   * Generates a response containing only a message.
   *
   * @param status  The HTTP status of the response.
   * @param message The message to include in the response.
   * @param <T>     The type of the response content.
   * @return The constructed response.
   */
  public static <T> Response<T> getResponseOnlyWithMessage(HttpStatus status, String message) {
    return Response.<T>builder().message(message).status(status.getReasonPhrase())
        .status(status.getReasonPhrase()).statusCode(status.value()).timeStamp(new Date().getTime())
        .build();
  }

  /**
   * Generates a success response with the provided message, content, and number of elements.
   *
   * @param status           The HTTP status of the response.
   * @param message          The message to include in the response.
   * @param content          The content of the response.
   * @param numberOfElements The number of elements in the response.
   * @param <T>              The type of the response content.
   * @return The constructed success response.
   */
  public static <T> Response<T> getSuccessResponse(HttpStatus status, String message, T content,
      int numberOfElements) {
    return Response.<T>builder().message(message).status(status.getReasonPhrase())
        .statusCode(status.value()).content(content).timeStamp(new Date().getTime())
        .numberOfElement(numberOfElements).build();
  }

  /**
   * Generates a list of custom error response DTOs from the binding result.
   *
   * @param result The binding result containing validation errors.
   * @return The list of custom error response DTOs.
   */
  private static List<ErrorResponseDto> getCustomError(BindingResult result) {
    List<ErrorResponseDto> dtoList = new ArrayList<>();
    result.getFieldErrors().forEach(fieldError -> {
      ErrorResponseDto errorResponseDto = ErrorResponseDto.builder().field(fieldError.getField())
          .message(fieldError.getDefaultMessage()).build();
      dtoList.add(errorResponseDto);
    });
    return dtoList;
  }
}
