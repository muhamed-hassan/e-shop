package com.cairoshop.service.impl;

import static com.cairoshop.configs.Constants.MAX_PAGE_SIZE;

import java.text.MessageFormat;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cairoshop.persistence.entities.User;
import com.cairoshop.persistence.repositories.UserRepository;
import com.cairoshop.service.UserService;
import com.cairoshop.service.exceptions.NoResultException;
import com.cairoshop.web.dtos.SavedItemsDTO;
import com.cairoshop.web.dtos.UserInBriefDTO;
import com.cairoshop.web.dtos.UserInDetailDTO;
import com.cairoshop.web.dtos.UserStatusDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@Service
public class UserServiceImpl
            extends BaseServiceImpl<User, UserInDetailDTO, UserInBriefDTO>
            implements UserService, UserDetailsService {

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        super(User.class, UserInBriefDTO.class, repository);
    }

    @Transactional
    @Override
    public void edit(int id, UserStatusDTO userStatusDTO) {
        try {
            var user = getRepository().getOne(id);
            user.setActive(userStatusDTO.isActive());
            user.setEnabled(userStatusDTO.isActive());
            getRepository().save(user);
        } catch (RuntimeException e) {
            if (e.getCause() != null && e.getCause().getClass() == EntityNotFoundException.class) {
                throw new NoResultException();
            }
            throw new RuntimeException(e);
        }

    }

    @Override
    public SavedItemsDTO<UserInBriefDTO> getAll(int startPosition, String sortBy, String sortDirection) {
        var pageable = PageRequest.of(startPosition, MAX_PAGE_SIZE, sortFrom(sortBy, sortDirection));
        var page = ((UserRepository) getRepository()).findAllCustomers(pageable);
        if (page.isEmpty()) {
            throw new NoResultException();
        }
        return new SavedItemsDTO<>(page.getContent(), (int) page.getTotalElements());
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return ((UserRepository) getRepository()).findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(MessageFormat.format("This user name {0} does not exist", username)));
    }

}
