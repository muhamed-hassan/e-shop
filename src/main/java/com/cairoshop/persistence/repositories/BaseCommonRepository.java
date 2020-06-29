package com.cairoshop.persistence.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@NoRepositoryBean
public interface BaseCommonRepository<SBDTO, T> extends JpaRepository<T, Integer>  {

    List<SBDTO> findAllBy(Pageable pageable);

    <SDDTO> Optional<SDDTO> findById(int id, Class<SDDTO> sdtoClass);

}
