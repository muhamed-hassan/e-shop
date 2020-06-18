package com.cairoshop.service;

import java.util.List;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public interface BaseClassificationService<NDTO, SDTO, T> extends BaseService<NDTO, SDTO, T>{

    void edit(SDTO sdto);

    List<SDTO> getAll();

}
