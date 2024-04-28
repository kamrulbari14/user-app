package com.kamrul.userapp.exception.handlers;

import com.kamrul.userapp.exception.ResponseProviderException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * The class {@code GlobalExceptionHandler} provides meaningful response to the client for
 * {@link ResponseProviderException} and {@link Exception}
 * @author  Kamrul Bari
 */

@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   * This handler provides a meaningful response to the client if anything wrong happens in the
   * server
   *
   * @param exception receives <em>ResponseProviderException</em> as parameter
   * @return returns a dynamic response to client for better understanding
   * @author Kamrul Bari
   */
  @ExceptionHandler(ResponseProviderException.class)
  public ResponseEntity<String> handleResponseException(ResponseProviderException exception) {
    int errorCode = exception.getErrorCode();
    HttpStatus status = HttpStatus.resolve(errorCode);

    // Default to Internal Server Error
    if (status == null) {
      status = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    return new ResponseEntity<>(exception.getMessage(), status);
  }

  /**
   * This handler provides Response for all other exception which is not recognised in
   * <em>ResponseProviderException</em>
   * @param exception receives <em>Exception</em> as parameter
   * @return returns a dynamic response to client for better understanding
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleException(Exception exception) {
    return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

}