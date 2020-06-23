package com.cairoshop.persistence.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@NoRepositoryBean
public interface BaseRepository<SBDTO, T> extends BaseCommonRepository<SBDTO, T>  {

    @Query("UPDATE #{#entityName} e SET e.active = false WHERE e.id = ?1")
    @Modifying
    int softDeleteById(int id);

}
