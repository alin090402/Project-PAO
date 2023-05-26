package org.example.utils;


import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

enum LogLevel {
    TRACE,
    DEBUG,
    INFO,
    WARN,
    ERROR

};


public class Audit {


    private LogLevel logLevel;

    private String filename;

    public static Audit instance;

    private Audit() {
        ConfigLoader configLoader = ConfigLoader.getInstance();
        logLevel = LogLevel.valueOf(configLoader.getAppProp().getProperty("logLevel"));
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String logFolder = rootPath + configLoader.getAppProp().getProperty("logFolder");
        // create a file with the current timestamp
        if (!new File(logFolder).exists()) {
            new File(logFolder).mkdirs();
        }
        filename = logFolder + "/" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".log";
        //create file
        try {
            new File(filename).createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Audit getInstance() {
        if (instance == null) {
            instance = new Audit();
        }
        return instance;
    }



    public void logTrace(String message) {
        log(LogLevel.TRACE, message);
    }

    public void logDebug(String message) {
        log(LogLevel.DEBUG, message);
    }

    public void logInfo(String message) {
        log(LogLevel.INFO, message);
    }

    public void logWarn(String message) {
        log(LogLevel.WARN, message);
    }

    public void logError(String message) {
        log(LogLevel.ERROR, message);
    }

    private void log(LogLevel importance, String message) {
        if (importance.ordinal() < logLevel.ordinal()) {
            return;
        }
        try {
            OutputStream outputStream = new FileOutputStream(filename, true);
            String logMessage = "[" +  importance + "] - " +  new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) + ": \t" + message + "\n";
            outputStream.write(logMessage.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
