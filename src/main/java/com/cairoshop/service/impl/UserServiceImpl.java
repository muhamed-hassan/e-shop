package com.cairoshop.service.impl;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cairoshop.configs.Constants;
import com.cairoshop.persistence.entities.User;
import com.cairoshop.persistence.repositories.UserRepository;
import com.cairoshop.service.UserService;
import com.cairoshop.service.exceptions.DataNotUpdatedException;
import com.cairoshop.web.dtos.SavedBriefCustomerDTO;
import com.cairoshop.web.dtos.SavedDetailedCustomerDTO;
import com.cairoshop.web.dtos.SavedItemsDTO;

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
    public void edit(int id, Map<String, String> newStatus) {
        String status = newStatus.get("status");
        if (StringUtils.isBlank(status) || (!status.equalsIgnoreCase("true") && !status.equalsIgnoreCase("false"))) {
            throw new IllegalArgumentException("Invalid newStatus payload");
        }
        int affectedRows = userRepository.update(id, Boolean.valueOf(status));
        if (affectedRows == 0) {
            throw new DataNotUpdatedException();
        }
    }

    @Override
    public SavedItemsDTO<SavedBriefCustomerDTO> getAllCustomers(int startPosition, String sortBy, String sortDirection) {
        List<SavedBriefCustomerDTO> page = userRepository.findAllCustomers(startPosition, Constants.MAX_PAGE_SIZE, sortBy, sortDirection);
        int countOfAllCustomers = userRepository.countAllCustomers();
        SavedItemsDTO<SavedBriefCustomerDTO> savedBriefCustomerDTOSavedItemsDTO = new SavedItemsDTO<>();
        savedBriefCustomerDTOSavedItemsDTO.setItems(page);
        savedBriefCustomerDTOSavedItemsDTO.setAllSavedItemsCount(countOfAllCustomers);
        return savedBriefCustomerDTOSavedItemsDTO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException(MessageFormat.format("This user name {0} does not exist", username)));
    }

}
