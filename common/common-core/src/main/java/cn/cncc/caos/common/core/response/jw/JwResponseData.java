package cn.cncc.caos.common.core.response.jw;

public class JwResponseData<T> {
  private int code;
  private String msg;
  private T data;

  /**
   * 请求成功时调用
   *
   * @param msg
   * @return
   */
  public static <T> JwResponseData<T> success(String msg) {
    return new JwResponseData<T>(msg);
  }

  /*public static <T> JwResponseData<T> success(T data){
      return new JwResponseData<T>(data);
  }*/
  public static <T> JwResponseData<T> success(String msg, T data) {
    return new JwResponseData<T>(msg, data);
  }

  public static <T> JwResponseData<T> error(JwResponseCode cm) {
    return new JwResponseData<T>(cm);
  }

  public static <T> JwResponseData<T> error(JwResponseCode cm, T data) {
    return new JwResponseData<T>(cm, data);
  }

  public JwResponseData() {
  }

  /**
   * 只传入数据默认成功，所以添加默认的code和msg
   *
   * @param msg
   */
  private JwResponseData(String msg) {
    this.code = 1;
    this.msg = msg;
    this.data = null;
  }

    /*private JwResponseData(T data) {
        this.code=0;
        this.msg="success";
        this.data=data;
    }*/

  private JwResponseData(String msg, T data) {
    this.code = 1;
    this.msg = msg;
    this.data = data;
  }


  private JwResponseData(JwResponseCode cm) {
    if (cm == null) {
      return;
    }
    this.code = cm.getCode();
    this.msg = cm.getMsg();
  }

  private JwResponseData(JwResponseCode cm, T data) {
    if (cm == null) {
      return;
    }
    this.code = cm.getCode();
    this.msg = cm.getMsg();
    this.data = data;
  }

  public int getCode() {
    return code;
  }

  public String getMsg() {
    return msg;
  }

  public T getData() {
    return data;
  }

  public boolean isSuccess() {
    return getCode() == JwResponseCode.SUCCESS.getCode();
  }
}

