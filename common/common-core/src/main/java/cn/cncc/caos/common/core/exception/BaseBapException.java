package cn.cncc.caos.common.core.exception;

public abstract class BaseBapException extends RuntimeException {
  public BaseBapException() {
    super();
  }

  public BaseBapException(String message) {
    super(message);
  }
}

