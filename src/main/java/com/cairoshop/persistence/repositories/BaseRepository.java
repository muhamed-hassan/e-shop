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
public interface BaseRepository<SDTO, T, ID> extends JpaRepository<T, ID>  {

    <SDTO> Optional<SDTO> findById(int id, Class<SDTO> sdtoClass);

    List<SDTO> findAllBy();

    List<SDTO> findAllBy(Pageable pageable);

    @Query("update #{#entityName} e set e.active = false where e.id =?1")
    @Modifying
    int softDeleteById(int id);

    @Query("update #{#entityName} e set e.name = ?1 where e.id =?2")
    @Modifying
    int update(String name, int id);

}
