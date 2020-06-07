package com.cairoshop;

import javax.inject.Singleton;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

@Singleton
public class GlobalLogger {

    public void doLogging(Level level, String msg, Class clazz, Throwable exception) {
        LogManager.getLogger(clazz).log(level, msg, exception);
    }

}
