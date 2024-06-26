package org.fss.service;

import org.fss.dto.UserDto;
import org.fss.entity.Role;
import org.fss.entity.User;
import org.fss.repository.RoleRepository;
import org.fss.repository.UserRepository;
import org.fss.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final RoleRepository roleRepository = Mockito.mock(RoleRepository.class);
    private final PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);

    @Test
    void test_saveUser_shouldCallSave() {
        UserServiceImpl userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder);
        Role role = new Role();
        UserDto userDto = new UserDto();
        when(passwordEncoder.encode(any())).thenReturn("password");
        when(roleRepository.findByName(any())).thenReturn(role);
        userService.saveUser(userDto);
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void test_findUserByEmail_shouldReturnUser() {
        UserServiceImpl userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder);
        User user = new User();
        user.setName("userName");
        when(userRepository.findByEmail(any())).thenReturn(user);

        User actualUser = userService.findUserByEmail("email");
        assertThat(actualUser.getName()).isEqualTo(user.getName());
    }


}
