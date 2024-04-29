package com.kamrul.userapp.exception.handlers;

import com.kamrul.userapp.dto.response.Response;
import com.kamrul.userapp.exception.ResponseProviderException;
import com.kamrul.userapp.util.builder.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * The class {@code GlobalExceptionHandler} provides meaningful response to the client for {@link
 * ResponseProviderException}, {@link NoResourceFoundException} and {@link Exception}
 *
 * @author Kamrul Bari
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
  public ResponseEntity<Response<String>> handleCustomizedResponseException(
      ResponseProviderException exception) {
    return new ResponseEntity<>(
        ResponseBuilder.getResponseOnlyWithMessage(exception.getHttpStatus(), exception.getMessage()),
        exception.getHttpStatus());
  }

  /**
   * This handler provides Response for unrecognized resources which throws
   * <em>NoResourceFoundException</em>
   *
   * @param exception receives <em>NoResourceFoundException</em> as parameter
   * @return returns a dynamic response to client for better understanding
   * @author Kamrul Bari
   */
  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<Response<String>> handleNoResourceFoundException(
      NoResourceFoundException exception) {
    return new ResponseEntity<>(
        ResponseBuilder.getResponseOnlyWithMessage(HttpStatus.NOT_FOUND, exception.getMessage()),
        HttpStatus.NOT_FOUND);
  }

  /**
   * This handler provides Response for unrecognized request methods which throws
   * <em>HttpRequestMethodNotSupportedException</em>
   *
   * @param exception receives <em>HttpRequestMethodNotSupportedException</em> as parameter
   * @return returns a dynamic response to client for better understanding
   * @author Kamrul Bari
   */
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<Response<String>> handleMethodNotSupported(
      HttpRequestMethodNotSupportedException exception) {
    return new ResponseEntity<>(
        ResponseBuilder.getResponseOnlyWithMessage(HttpStatus.METHOD_NOT_ALLOWED, exception.getMessage()),
        HttpStatus.METHOD_NOT_ALLOWED);
  }

  /**
   * This handler provides Response for all other exception which is not recognised
   *
   * @param exception receives <em>Exception</em> as parameter
   * @return returns a dynamic response to client for better understanding
   * @author Kamrul Bari
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Response<String>> handleException(Exception exception) {
    return new ResponseEntity<>(ResponseBuilder.getResponseOnlyWithMessage(HttpStatus.INTERNAL_SERVER_ERROR,
        exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
  }

}