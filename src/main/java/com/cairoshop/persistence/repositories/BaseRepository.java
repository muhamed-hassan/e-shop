package com.cairoshop.persistence.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
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

//    <SDTO> Optional<SDTO> findById(int id, Class<SDTO> sdtoClass);

//    List<SDTO> findAllBy(Pageable pageable);

    @Query("UPDATE #{#entityName} e SET e.active = false WHERE e.id = ?1")
    @Modifying
    int softDeleteById(int id);

}
