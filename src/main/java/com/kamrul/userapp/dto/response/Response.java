package com.kamrul.userapp.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Response<T> {

  private Long timeStamp;
  private int statusCode;
  private String status;
  private String message;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private T content;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private int numberOfElement;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Long rowCount;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<ErrorResponseDto> errors;
}
