package com.cairoshop.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cairoshop.persistence.entities.User;
import com.cairoshop.persistence.repositories.UserRepository;
import com.cairoshop.service.exceptions.DataNotUpdatedException;
import com.cairoshop.service.impl.UserServiceImpl;
import com.cairoshop.web.dtos.SavedItemsDTO;
import com.cairoshop.web.dtos.UserInBriefDTO;
import com.cairoshop.web.dtos.UserInDetailDTO;
import com.cairoshop.web.dtos.UserStatusDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class UserServiceTest
            extends BaseCommonServiceTest<User, UserInDetailDTO> {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    protected UserServiceTest() {
        super(User.class, UserInDetailDTO.class);
    }

    @BeforeEach
    public void injectRefs() {
        injectRefs(userRepository, userService);
    }

    @Test
    public void testGetById_WhenDataFound_ThenReturnIt() throws Exception {
        UserInDetailDTO userInDetailDTO = new UserInDetailDTO("username", "email", "phone", "address", true, "name");
        testGetById_WhenDataFound_ThenReturnIt(1, userInDetailDTO, List.of("getName", "getUsername", "getEmail", "getPhone", "getAddress", "isActive"));
    }

    @Test
    public void testGetById_WhenDataNotFound_ThenThrowNoResultException() {
        testGetById_WhenDataNotFound_ThenThrowNoResultException(404);
    }

    @Test
    public void testGetAllCustomersByPage_WhenDataFound_ThenReturnIt() {
        UserInBriefDTO userInBriefDTO = new UserInBriefDTO(1, "name", true);
        List<UserInBriefDTO> page = List.of(userInBriefDTO);
        when(userRepository.findAllCustomers(any(int.class), any(int.class), anyString(), anyString()))
            .thenReturn(page);
        int countOfAllCustomers = 1;
        when(userRepository.countAllCustomers())
            .thenReturn(countOfAllCustomers);
        SavedItemsDTO<UserInBriefDTO> expectedResult = new SavedItemsDTO<>();
        expectedResult.setItems(page);
        expectedResult.setAllSavedItemsCount(countOfAllCustomers);

        SavedItemsDTO<UserInBriefDTO> actualResult = userService.getAllCustomers(0, "name", "ASC");

        assertEquals(expectedResult.getAllSavedItemsCount(), actualResult.getAllSavedItemsCount());
        assertIterableEquals(expectedResult.getItems(), actualResult.getItems());
    }

    @Test
    public void testEdit_WhenDataIsValid_ThenSave() {
        int id = 1;
        UserStatusDTO userStatusDTO = new UserStatusDTO();
        userStatusDTO.setActive(false);
        int affectedRows = 1;
        when(userRepository.update(id, userStatusDTO.isActive()))
            .thenReturn(affectedRows);

        userService.edit(id, userStatusDTO);

        verify(userRepository).update(id, userStatusDTO.isActive());
    }

    @Test
    public void testEdit_WhenRecordNotUpdated_ThenThrowDataNotUpdatedException() {
        int id = 1;
        UserStatusDTO userStatusDTO = new UserStatusDTO();
        userStatusDTO.setActive(false);
        int affectedRows = 0;
        when(userRepository.update(any(int.class), any(boolean.class)))
            .thenReturn(affectedRows);

        assertThrows(DataNotUpdatedException.class,
            () -> userService.edit(id, userStatusDTO));
    }

    @Test
    public void testLoadUserByUsername_WhenDataFound_ThenReturnIt() {
        String username = "username";
        User user = new User();
        user.setUsername(username);
        Optional<User> expectedResult = Optional.of(user);
        when(userRepository.findByUsername(anyString()))
            .thenReturn(expectedResult);

        UserDetails actualResult = userService.loadUserByUsername(username);

        assertEquals(username, actualResult.getUsername());
    }

    @Test
    public void testLoadUserByUsername_WhenDataNotFound_ThenThrowUsernameNotFoundException() {
        String username = "username";
        Optional<User> expectedResult = Optional.empty();
        when(userRepository.findByUsername(anyString()))
            .thenReturn(expectedResult);

        assertThrows(UsernameNotFoundException.class,
            () -> userService.loadUserByUsername(username));
    }

}
