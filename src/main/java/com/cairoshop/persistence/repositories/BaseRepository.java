package com.cairoshop.persistence.repositories;

import java.util.List;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public interface BaseRepository<T, DDTO, BDTO>
            extends BaseCommonRepository<DDTO>  {

    int save(T entity) throws Exception;

    int countAllActive();

    List<BDTO> findAllByPage(int startPosition, int pageSize, String sortBy, String sortDirection);

    int update(int id, DDTO ddto) throws Exception;

    int deleteById(int id);

}
