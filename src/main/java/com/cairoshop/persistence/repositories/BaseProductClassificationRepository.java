package com.cairoshop.persistence.repositories;

import java.util.List;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public interface BaseProductClassificationRepository<T, D, B>
            extends BaseRepository<T, D, B> {

    List<B> findAll();

    boolean safeToDelete(int productClassificationId);

}
