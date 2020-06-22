package com.cairoshop.service.impl;

import java.text.MessageFormat;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cairoshop.persistence.entities.User;
import com.cairoshop.persistence.repositories.UserRepository;
import com.cairoshop.service.UserService;
import com.cairoshop.service.exceptions.DataNotUpdatedException;
import com.cairoshop.web.dtos.NewCustomerStateDTO;
import com.cairoshop.web.dtos.SavedBriefCustomerDTO;
import com.cairoshop.web.dtos.SavedDetailedCustomerDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@Service
public class UserServiceImpl
                extends BaseCommonServiceImpl<SavedDetailedCustomerDTO, SavedBriefCustomerDTO, User>
                implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public UserServiceImpl() {
        super(SavedDetailedCustomerDTO.class);
    }

    @PostConstruct
    public void injectRefs() {
        setRepos(userRepository);
    }

    @Transactional
    @Override
    public void edit(NewCustomerStateDTO newCustomerStateDTO) {
        int affectedRows = userRepository.update(newCustomerStateDTO.getId(), newCustomerStateDTO.isActive());
        if (affectedRows == 0) {
            throw new DataNotUpdatedException();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(MessageFormat.format("This user name {0} does not exist", username)));
    }

}
