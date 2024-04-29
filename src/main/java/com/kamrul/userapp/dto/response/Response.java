package com.kamrul.userapp.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response<T> {
    @JsonInclude
    private Long timeStamp;
    @JsonInclude
    private int statusCode;
    @JsonInclude
    private String status;
    @JsonInclude
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
