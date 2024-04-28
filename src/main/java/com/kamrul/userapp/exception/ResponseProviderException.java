package com.kamrul.userapp.exception;

import lombok.Getter;

/**
 * This Custom Exception is to handle any kind
 * of error occurred while giving API response
 */

@Getter
public class ResponseProviderException extends RuntimeException {

  /**
   * <b>errorCode</b> is for handling <em>HttpStatus</em> codes to make API response more dynamic
   */
  private final int errorCode;

  public ResponseProviderException(String message, int errorCode) {
    super(message);
    this.errorCode = errorCode;
  }

}
