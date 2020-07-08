package com.cairoshop.service;

import java.util.List;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public interface BaseProductClassificationService<DDTO, BDTO>
            extends BaseService<DDTO, BDTO> {

    void edit(int id, DDTO ddto);

    List<BDTO> getAll();

}
