package com.cairoshop.persistence.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public interface BaseCommonRepository<DDTO> {



    /*<DDTO> Optional<DDTO> findById(int id, Class<DDTO> sdtoClass);

    @Query("SELECT new ?1 FROM #{#entityName} e WHERE e.id = ?2")
    <DDTO> Optional<DDTO> findById(String constructor, int id, Class<DDTO> sdtoClass);*/
    DDTO findById(int id);


}
