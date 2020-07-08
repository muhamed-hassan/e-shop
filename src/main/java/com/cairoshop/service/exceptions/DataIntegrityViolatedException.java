package com.cairoshop.service.exceptions;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class DataIntegrityViolatedException extends RuntimeException {

    public DataIntegrityViolatedException() {
        super("DB constraint(s) violated");
    }

}

