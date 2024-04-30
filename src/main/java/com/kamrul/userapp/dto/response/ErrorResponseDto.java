package com.kamrul.userapp.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO class representing an error response.
 */
@Getter
@Setter
@Builder
public class ErrorResponseDto {

  /**
   * The field associated with the error.
   */
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String field;

  /**
   * The error message.
   */
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String message;
}
