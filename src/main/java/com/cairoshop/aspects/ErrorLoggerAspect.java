package com.cairoshop.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@Aspect
@Component
public class ErrorLoggerAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorLoggerAspect.class);

    @AfterThrowing(pointcut = "within(com.cairoshop.service.impl.*)", throwing= "exception")
    public void logExceptionMessage(JoinPoint joinPoint, Exception exception) {
        StringBuilder errorMsg = new StringBuilder()
            .append(">> Error occurred in ")
            .append(joinPoint.getSignature().toLongString())
            .append(" , with message: ")
            .append(exception.getMessage() == null ? "Internal error" : exception.getMessage());
        LOGGER.error(errorMsg.toString());
    }

}
