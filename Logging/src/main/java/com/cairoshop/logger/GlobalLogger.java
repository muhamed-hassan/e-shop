package com.cairoshop.logger;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public class GlobalLogger {

    private volatile static GlobalLogger globalLogger;

    private GlobalLogger() {

    }

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

    public void doLogging(Level level, String msg, Throwable exception) {

        Logger logger = LogManager.getLogger(this.getClass());
        logger.log(level, msg, exception);

    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        globalLogger = null;
        System.gc();
    }
}
