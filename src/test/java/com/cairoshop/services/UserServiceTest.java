package com.cairoshop.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import com.cairoshop.persistence.entities.User;
import com.cairoshop.persistence.repositories.UserRepository;
import com.cairoshop.service.impl.UserServiceImpl;
import com.cairoshop.web.dtos.SavedBriefCustomerDTO;
import com.cairoshop.web.dtos.SavedDetailedCustomerDTO;
import com.cairoshop.web.dtos.SavedItemsDTO;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest
        extends BaseCommonServiceTest<SavedDetailedCustomerDTO, SavedBriefCustomerDTO, User> {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    protected UserServiceTest() {
        super(User.class, SavedDetailedCustomerDTO.class);
    }

    @BeforeEach
    public void injectRefs() {
        injectRefs(userRepository, userService);
    }

    @Test
    public void testGetById_WhenDataFound_ThenReturnIt() throws Exception {
        SavedDetailedCustomerDTO savedDetailedCustomerDTO = new SavedDetailedCustomerDTO(1, "name", true, "username", "email", "phone", "address");
        testGetById_WhenDataFound_ThenReturnIt(savedDetailedCustomerDTO, List.of("getId", "getName", "isActive", "getUsername", "getEmail", "getPhone", "getAddress"));
    }

    @Test
    public void testGetAllCustomersByPage_WhenDataFound_ThenReturnIt() {
        SavedBriefCustomerDTO savedBriefCustomerDTO = new SavedBriefCustomerDTO(1, "name", true);
        List<SavedBriefCustomerDTO> page = List.of(savedBriefCustomerDTO);
        when(userRepository.findAllCustomers(any(int.class), any(int.class), anyString(), anyString()))
            .thenReturn(page);
        int countOfAllCustomers = 1;
        when(userRepository.countAllCustomers())
            .thenReturn(countOfAllCustomers);
        SavedItemsDTO<SavedBriefCustomerDTO> expectedResult = new SavedItemsDTO<>();
        expectedResult.setItems(page);
        expectedResult.setAllSavedItemsCount(countOfAllCustomers);

        SavedItemsDTO<SavedBriefCustomerDTO> actualResult = userService.getAllCustomers(0, "name", "ASC");

        assertEquals(expectedResult.getAllSavedItemsCount(), actualResult.getAllSavedItemsCount());
        assertIterableEquals(expectedResult.getItems(), actualResult.getItems());
    }

    @Test
    public void testEdit_WhenDataIsValid_ThenSave() throws Exception {
        int id = 1;
        Map<String, String> newStatus = Map.of("status", "true");
        int affectedRows = 1;
        when(userRepository.update(id, Boolean.valueOf(newStatus.get("status"))))
            .thenReturn(affectedRows);

        userService.edit(id, newStatus);

        verify(userRepository, times(1)).update(id, Boolean.valueOf(newStatus.get("status")));
    }

    @Test
    public void testLoadUserByUsername_WhenDataFound_ThenReturnIt() throws Exception {
        String username = "username";
        User user = new User();
        user.setUsername(username);
        Optional<User> expectedResult = Optional.of(user);
        when(userRepository.findByUsername(anyString()))
            .thenReturn(expectedResult);

        UserDetails actualResult = userService.loadUserByUsername(username);

        assertEquals(username, actualResult.getUsername());
    }

}
