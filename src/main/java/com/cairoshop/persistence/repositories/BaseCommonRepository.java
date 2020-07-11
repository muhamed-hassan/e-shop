package com.cairoshop.persistence.repositories;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public interface BaseCommonRepository<DDTO> {

    DDTO findById(int id);

}
