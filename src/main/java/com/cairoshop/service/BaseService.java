package com.cairoshop.service;

import java.util.List;
import java.util.Map;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public interface BaseService<NDTO, SDTO, T> {

    int add(NDTO ndto);

    void edit(Map<String, Object> fields);

    SDTO getById(int id);

    void removeById(int id);

    List<SDTO> getAll();

    List<SDTO> getAll(int startPosition, String sortBy, String sortDirection);

}
