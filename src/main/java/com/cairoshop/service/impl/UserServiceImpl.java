package com.cairoshop.service.impl;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cairoshop.persistence.entities.User;
import com.cairoshop.persistence.repositories.UserRepository;
import com.cairoshop.service.UserService;
import com.cairoshop.service.exceptions.DataNotUpdatedException;
import com.cairoshop.web.dtos.NewCustomerDTO;
import com.cairoshop.web.dtos.SavedCustomerDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@Service
public class UserServiceImpl extends BaseServiceImpl<NewCustomerDTO, SavedCustomerDTO, User> implements UserService {

    @Autowired
    private UserRepository userRepository;

    public UserServiceImpl() {
        super(User.class, SavedCustomerDTO.class);
    }

    @PostConstruct
    public void injectRefs() {
        setRepos(userRepository);
    }

    @Transactional
    @Override
    public void edit(Map<String, Object> fields) {
        int affectedRows = userRepository.update((int) fields.get("id"), (boolean) fields.get("active"));
        if (affectedRows == 0) {
            throw new DataNotUpdatedException();
        }
    }

}
