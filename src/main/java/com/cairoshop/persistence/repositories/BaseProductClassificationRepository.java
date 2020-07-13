package com.cairoshop.persistence.repositories;

import java.util.List;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public interface BaseProductClassificationRepository<T, DDTO, BDTO>
            extends BaseRepository<T, DDTO, BDTO> {

    List<BDTO> findAll();

    boolean safeToDelete(int productClassificationId);

}
