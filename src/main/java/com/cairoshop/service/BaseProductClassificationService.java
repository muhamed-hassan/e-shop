package com.cairoshop.service;

import java.util.List;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public interface BaseProductClassificationService<D, B>
            extends BaseService<D, B> {

    void edit(int id, D detailedDto);

    List<B> getAll();

}
