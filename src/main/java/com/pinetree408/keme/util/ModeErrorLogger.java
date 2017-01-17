package com.pinetree408.keme.util;

/** Created by user on 2017-01-11. */
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.jnativehook.keyboard.NativeKeyEvent;

public class ModeErrorLogger {

  private File logFile;

  public ModeErrorLogger() {}

  public ModeErrorLogger(String fileName) {
    logFile = new File(fileName);
  }

  public void log(
      NativeKeyEvent e,
      String nowLanguage,
      String nowTopProcess,
      String reocverState,
      String preventState) {

    Date d = new Date(e.getWhen());
    SimpleDateFormat dateformat =
        new SimpleDateFormat("EEE MMM d HH:mm:ss:SSS z yyyy", Locale.KOREA);
    String date = dateformat.format(d);

    String logString =
        date
            + "-"
            + NativeKeyEvent.getKeyText(e.getKeyCode())
            + "-"
            + nowLanguage
            + "-"
            + nowTopProcess
            + "-"
            + reocverState
            + "-"
            + preventState
            + "\r\n";

    try {
      FileWriter fw = new FileWriter(this.logFile, true);
      fw.write(logString);
      fw.close();
    } catch (IOException exception) {
      // TODO Auto-generated catch block
      exception.printStackTrace();
    }
  }
}
