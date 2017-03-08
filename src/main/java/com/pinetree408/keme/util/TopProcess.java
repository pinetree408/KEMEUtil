package com.pinetree408.keme.util;

/**
 * Created by user on 2017-03-08.
 */
public class TopProcess {
  static Util util;

  static String prevTopProcess;
  static String nowTopProcess;
  static String nowLanguage;

  public TopProcess() {
    util = new Util();

    prevTopProcess = "initial";
    nowTopProcess = "initial";
    nowLanguage = "initial";
  }

  public String getNowTopProcess() {
    return nowTopProcess;
  }

  public String getNowLanguage() {
    return nowLanguage;
  }

  public boolean isChangeProcess() {

    nowTopProcess = util.nowTopProcess();

    if (!nowTopProcess.equals("")) {

      if (!prevTopProcess.equals(nowTopProcess)) {

        prevTopProcess = util.nowTopProcess();

        nowLanguage = util.nowLanguage();

        return true;
      }
    }
    return false;
  }
}
