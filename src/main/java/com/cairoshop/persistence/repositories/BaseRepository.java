package com.cairoshop.persistence.repositories;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import com.cairoshop.persistence.entities.Category;
import com.cairoshop.web.dtos.SavedCategoryDTO;
@NoRepositoryBean
public interface BaseRepository<SDTO, T, ID> extends JpaRepository<T, ID>  {
    //@Query(value = "select id, name, active from category where id = :id", nativeQuery = true)
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
