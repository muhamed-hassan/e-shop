package com.cairoshop.logger;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public class GlobalLogger {

    private volatile static GlobalLogger globalLogger;

    private GlobalLogger() { }

    public static GlobalLogger getInstance() {

        if (globalLogger == null) {

            synchronized (GlobalLogger.class) {
                if (globalLogger == null) {
                    globalLogger = new GlobalLogger();
                }
            }

        }

        return globalLogger;
    }

    public void doLogging(Level level, String msg, Throwable exception, Class callingClass) {

        LogManager
                .getLogger(callingClass)
                .log(level, msg, exception);

    }

}
