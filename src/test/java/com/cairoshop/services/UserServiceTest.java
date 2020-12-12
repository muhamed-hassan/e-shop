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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cairoshop.persistence.entities.User;
import com.cairoshop.persistence.repositories.UserRepository;
import com.cairoshop.service.UserService;
import com.cairoshop.service.impl.UserServiceImpl;
import com.cairoshop.web.dtos.UserInBriefDTO;
import com.cairoshop.web.dtos.UserInDetailDTO;
import com.cairoshop.web.dtos.UserStatusDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
class UserServiceTest
        extends BaseServiceTest<User, UserInDetailDTO, UserInBriefDTO> {

    UserServiceTest() {
        super(User.class);
    }

    @BeforeEach
    void injectRefs() {
        var userService = new UserServiceImpl(mock(UserRepository.class));
        injectRefs(userService);
    }

    @Test
    void testEdit_WhenDataIsValid_ThenSave() {
        var userEntity = mock(User.class);
        var id = 1;
        var userStatusDTO = new UserStatusDTO();
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
    void testGetById_WhenDataFound_ThenReturnIt()
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        var userInDetailDTO = Optional.of(UserInDetailDTO.builder()
                                                                                    .name("some name")
                                                                                    .username("some username")
                                                                                    .email("malekshda3wah@leehkeda.com")
                                                                                    .phone("0151212002")
                                                                                    .address("henak")
                                                                                .build());
        var getters = List.of("getUsername", "getEmail", "getPhone", "getAddress", "getName");
        testGetById_WhenDataFound_ThenReturnIt(1, userInDetailDTO, getters);
    }

    @Test
    void testGetAllCustomersByPage_WhenDataFound_ThenReturnIt() {
        var userInBriefDTO = new UserInBriefDTO(1, "name", true);
        var page = mock(Page.class);
        when(page.getContent())
            .thenReturn(List.of(userInBriefDTO));
        when(page.getTotalElements())
            .thenReturn(1L);
        when(((UserRepository) getRepository()).findAllCustomers(any(Pageable.class)))
            .thenReturn(page);

        var actualResult = getService().getAll(0, "id", "ASC");

        assertEquals(Long.valueOf(page.getTotalElements()).intValue(), actualResult.getAllSavedItemsCount());
        assertIterableEquals(page.getContent(), actualResult.getItems());
    }

    @Test
    void testLoadUserByUsername_WhenDataFound_ThenReturnIt() {
        var username = "username";
        var userEntity = new User();
        userEntity.setUsername(username);
        var expectedResult = Optional.of(userEntity);
        when(((UserRepository) getRepository()).findByUsername(anyString()))
            .thenReturn(expectedResult);

        var actualResult = ((UserDetailsService) getService()).loadUserByUsername(username);

        assertEquals(username, actualResult.getUsername());
    }

    @Test
    void testLoadUserByUsername_WhenDataNotFound_ThenThrowUsernameNotFoundException() {
        var username = "username";
        Optional<User> expectedResult = Optional.empty();
        when(((UserRepository) getRepository()).findByUsername(anyString()))
            .thenReturn(expectedResult);

        assertThrows(UsernameNotFoundException.class,
            () -> ((UserDetailsService) getService()).loadUserByUsername(username));
    }

}
