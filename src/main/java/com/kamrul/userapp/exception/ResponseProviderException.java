package com.kamrul.userapp.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * This Custom Exception is to handle any kind
 * of error occurred while giving API response
 */

@Getter
public class ResponseProviderException extends RuntimeException {

  /**
   * <b>errorCode</b> is for handling <em>HttpStatus</em> codes to make API response more dynamic
   */
  private final HttpStatus httpStatus;

  public ResponseProviderException(String message, HttpStatus httpStatus) {
    super(message);
    this.httpStatus = httpStatus;
  }

}
