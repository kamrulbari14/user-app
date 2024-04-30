package com.kamrul.userapp.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO class representing a response from the API.
 */
@Getter
@Setter
@Builder
public class Response<T> {

  /**
   * The timestamp when the response was generated.
   */
  private Long timeStamp;

  /**
   * The HTTP status code of the response.
   */
  private int statusCode;

  /**
   * The status message of the response.
   */
  private String status;

  /**
   * The message content of the response.
   */
  private String message;

  /**
   * The content of the response.
   */
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private T content;

  /**
   * The number of elements in the response.
   */
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private int numberOfElement;

  /**
   * The row count of the response.
   */
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Long rowCount;

  /**
   * The list of errors in the response.
   */
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<ErrorResponseDto> errors;
}
