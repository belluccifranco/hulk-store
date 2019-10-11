package com.todo1.hulkstore.exceptions;

public class BusinessServiceException extends RuntimeException {

  public BusinessServiceException() {
    super();
  }

  public BusinessServiceException(String message) {
    super(message);
  }

  public BusinessServiceException(Throwable cause) {
    super(cause);
  }

  public BusinessServiceException(String message, Throwable cause) {
    super(message, cause);
  }
}
