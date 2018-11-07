package com.dcdz.loglibrary;

import android.os.Environment;

import org.apache.log4j.Level;

import java.io.File;

/**
 * Created by ljw on 2017/3/29.
 */
public class Log4jConfigure {
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 10;
    private static final String DEFAULT_LOG_FILE_NAME = "db_wan_android.txt";
    //private static final String DEFAULT_LOG_FILE_NAME = "log";
    private static final String TAG = "Log4jConfigure";
    // 对应AndroidManifest文件中的package
    private static final String PACKAGE_NAME = "com.dcdz.wanandroid";

    private static void configure(String fileName, String filePath) {
        final LogConfigurator logConfigurator = new LogConfigurator();
        String filedir = "";

        try {
            if (isSdcardMounted()) {
                filedir = Environment.getExternalStorageDirectory().getAbsolutePath();
            } else {
                filedir = filePath;
            }

            fileName = filedir + File.separator + "ljw_wan_android" + File.separator + "log" + File.separator + fileName;

            //设置文件名
            logConfigurator.setFileName(fileName);

            //设置 输出到日志文件的文字格式 默认 %d %-5p [%c{2}]-[%L] %m%n
            //logConfigurator.setFilePattern("%d %-5p [%c{2}]-[%L] %m%n");
            //logConfigurator.setFilePattern("==============%newline%d{yyyy-MM-dd HH:mm:ss:SSS} [%thread] %-5p [%c{2}]-[%F%L] %m%n");
            //logConfigurator.setFilePattern("%d{yyyy-MM-dd HH:mm:ss:SSS} [%thread] %-5p [%c{2}]-[%F%L] %m%n");
            //logConfigurator.setFilePattern("%d %-5p [%c{2}]-[%L] %m%n");
            logConfigurator.setFilePattern("%d [%-5p] %m\r\n");
            //设置输出到控制台的文字格式 默认%m%n
            logConfigurator.setLogCatPattern("%m%n");
            //设置总文件大小
            logConfigurator.setMaxFileSize(1024 * 1024 * 5);
            //设置最大产生的文件个数
            logConfigurator.setMaxBackupSize(30);

            //以下为通用配置
            logConfigurator.setImmediateFlush(true);
            //设置root日志输出级别 默认为DEBUG
            logConfigurator.setRootLevel(Level.DEBUG);
            // 设置日志输出级别
            logConfigurator.setLevel("org.apache", Level.INFO);


            logConfigurator.configure();

            android.util.Log.e(TAG, "Log4j config finish");
        } catch (Throwable throwable) {
            logConfigurator.setResetConfiguration(true);
            android.util.Log.e(TAG, "Log4j config error, use default config. Error:" + throwable);
        }
    }

    public static void configure(String filePath) {
        configure(DEFAULT_LOG_FILE_NAME,filePath);
    }

    private static boolean isSdcardMounted() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }
}
