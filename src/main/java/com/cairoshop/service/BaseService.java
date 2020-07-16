package com.cairoshop.service;

import java.lang.reflect.InvocationTargetException;

import com.cairoshop.web.dtos.SavedItemsDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public interface BaseService<D, B> {

    int add(D detailedDto) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException;

    void removeById(int id) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;

    D getById(int id);

    SavedItemsDTO<B> getAll(int startPosition, String sortBy, String sortDirection);

}
