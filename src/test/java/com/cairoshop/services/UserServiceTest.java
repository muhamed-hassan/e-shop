package com.cairoshop.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cairoshop.persistence.entities.User;
import com.cairoshop.persistence.repositories.UserRepository;
import com.cairoshop.service.UserService;
import com.cairoshop.service.impl.UserServiceImpl;
import com.cairoshop.web.dtos.SavedItemsDTO;
import com.cairoshop.web.dtos.UserInBriefDTO;
import com.cairoshop.web.dtos.UserInDetailDTO;
import com.cairoshop.web.dtos.UserStatusDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class UserServiceTest
            extends BaseServiceTest<User, UserInDetailDTO, UserInBriefDTO> {

    protected UserServiceTest() {
        super(User.class);
    }

    @BeforeEach
    public void injectRefs() {
        UserServiceImpl userService = new UserServiceImpl(mock(UserRepository.class));
        injectRefs(userService);
    }

    @Test
    public void testEdit_WhenDataIsValid_ThenSave() {
        User userEntity = mock(User.class);
        int id = 1;
        UserStatusDTO userStatusDTO = new UserStatusDTO();
        userStatusDTO.setActive(false);
        when(getRepository().getOne(id))
            .thenReturn(userEntity);
        when(getRepository().save(userEntity))
            .thenReturn(userEntity);

        ((UserService) getService()).edit(id, userStatusDTO);

        verify(getRepository()).getOne(id);
        verify(getRepository()).save(userEntity);
    }

    @Test
    public void testGetById_WhenDataFound_ThenReturnIt()
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Optional<UserInDetailDTO> userInDetailDTO = Optional.of(new UserInDetailDTO("username", "email", "phone", "address", "name"));
        testGetById_WhenDataFound_ThenReturnIt(1, userInDetailDTO, List.of("getUsername", "getEmail", "getPhone", "getAddress", "getName"));
    }

    @Test
    public void testGetAllCustomersByPage_WhenDataFound_ThenReturnIt() {
        UserInBriefDTO userInBriefDTO = new UserInBriefDTO(1, "name", true);
        Page<UserInBriefDTO> page = mock(Page.class);
        when(page.getContent())
            .thenReturn(List.of(userInBriefDTO));
        when(page.getTotalElements())
            .thenReturn(1L);
        when(((UserRepository) getRepository()).findAllCustomers(any(Pageable.class)))
            .thenReturn(page);

        SavedItemsDTO<UserInBriefDTO> actualResult = getService().getAll(0, "id", "ASC");

        assertEquals(Long.valueOf(page.getTotalElements()).intValue(), actualResult.getAllSavedItemsCount());
        assertIterableEquals(page.getContent(), actualResult.getItems());
    }

    @Test
    public void testLoadUserByUsername_WhenDataFound_ThenReturnIt() {
        String username = "username";
        User userEntity = new User();
        userEntity.setUsername(username);
        Optional<User> expectedResult = Optional.of(userEntity);
        when(((UserRepository) getRepository()).findByUsername(anyString()))
            .thenReturn(expectedResult);

        UserDetails actualResult = ((UserDetailsService) getService()).loadUserByUsername(username);

        assertEquals(username, actualResult.getUsername());
    }

    @Test
    public void testLoadUserByUsername_WhenDataNotFound_ThenThrowUsernameNotFoundException() {
        String username = "username";
        Optional<User> expectedResult = Optional.empty();
        when(((UserRepository) getRepository()).findByUsername(anyString()))
            .thenReturn(expectedResult);

        assertThrows(UsernameNotFoundException.class,
            () -> ((UserDetailsService) getService()).loadUserByUsername(username));
    }

}
