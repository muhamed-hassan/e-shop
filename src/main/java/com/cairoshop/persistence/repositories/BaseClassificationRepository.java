package com.cairoshop.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@NoRepositoryBean
public interface BaseClassificationRepository<SDTO, T, ID> extends BaseRepository<SDTO, T, ID> {

    @Query("UPDATE #{#entityName} c SET c.name = ?2 WHERE c.id = ?1")
    @Modifying
    int update(int id, String name);

    List<SDTO> findAllBy();

}
