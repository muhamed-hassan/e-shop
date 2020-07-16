package com.cairoshop.persistence.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@NoRepositoryBean
public interface BaseRepository<T, D, B>
            extends JpaRepository<T, Integer> {

    Optional<D> findByIdAndActive(int id, boolean active);

    Page<B> findAllByActive(boolean active, Pageable pageable, Class<B> briefDtoClass);

}
