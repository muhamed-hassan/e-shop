package com.cairoshop.persistence.repositories;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public interface BaseRepository<T, D, B>
            extends BaseCommonRepository<D>  {

    int save(T entity) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;

    int countAllActive();

    List<B> findAllByPage(int startPosition, int pageSize, String sortBy, String sortDirection);

    int update(int id, D detailedDtoClass) throws InvocationTargetException, IllegalAccessException;

    int deleteById(int id);

}
