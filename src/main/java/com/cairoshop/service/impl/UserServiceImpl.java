package com.cairoshop.service.impl;

import static com.cairoshop.configs.Constants.MAX_PAGE_SIZE;

import java.text.MessageFormat;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    private UserRepository userRepository;

    protected UserServiceImpl() {
        super(User.class, UserInBriefDTO.class);
    }

    @PostConstruct
    public void injectRefs() {
        setRepo(userRepository);
    }

    @Transactional
    @Override
    public void edit(int id, UserStatusDTO userStatusDTO) {
        User user = getRepository().getOne(id);
        user.setActive(userStatusDTO.isActive());
        user.setEnabled(userStatusDTO.isActive());
        getRepository().save(user);
    }

    @Override
    public SavedItemsDTO<UserInBriefDTO> getAll(int startPosition, String sortBy, String sortDirection) {
        Page<UserInBriefDTO> page = userRepository.findAllCustomers(PageRequest.of(startPosition, MAX_PAGE_SIZE, sortFrom(sortBy, sortDirection)));
        if (page.isEmpty()) {
            throw new NoResultException();
        }
        return new SavedItemsDTO<>(page.getContent(), Long.valueOf(page.getTotalElements()).intValue());
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException(MessageFormat.format("This user name {0} does not exist", username)));
    }

}
