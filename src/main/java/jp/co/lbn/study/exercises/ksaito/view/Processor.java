package jp.co.lbn.study.exercises.ksaito.view;

import java.io.BufferedReader;
import jp.co.lbn.study.exercises.ksaito.exception.AppException;

/**
 * 業務処理インターフェース.
 */
public interface Processor {
  void process(BufferedReader br) throws AppException;

  View nextView();
}
