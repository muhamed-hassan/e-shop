package com.cairoshop.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@NoRepositoryBean
public interface BaseProductClassificationRepository<T, D, B>
            extends BaseRepository<T, D, B> {

    List<B> findAllByActive(boolean active, Class<B> briefDtoClass);

    long countOfAssociationsWithProduct(int productClassificationId);

}
