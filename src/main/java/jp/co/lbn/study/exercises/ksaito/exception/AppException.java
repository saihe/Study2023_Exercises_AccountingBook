package jp.co.lbn.study.exercises.ksaito.exception;

/**
 * 業務エラー.
 */
public class AppException extends Exception {
  public AppException(String msg) {
    super(msg);
  }

  public AppException(Exception e) {
    super(e);
  }

  public static AppException internal() {
    return new AppException("予期しないエラーが発生しました。");
  }
}
