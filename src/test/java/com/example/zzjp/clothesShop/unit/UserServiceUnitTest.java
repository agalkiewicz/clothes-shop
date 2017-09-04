package com.example.zzjp.clothesShop.unit;

import com.example.zzjp.clothesShop.model.*;
import com.example.zzjp.clothesShop.repository.UserRepository;
import com.example.zzjp.clothesShop.service.UserService;
import com.example.zzjp.clothesShop.util.ObjectMock;
import com.example.zzjp.clothesShop.util.UserFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceUnitTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    UserFactory userFactory;

    UserService userService;

    @Before
    public void setUp() {
        userService = new UserService(userRepository, passwordEncoder, userFactory);
    }

    @Test
    public void shouldGetUserById() {
        User user = new User();

        when(userRepository.findOne(ObjectMock.ID))
                .thenReturn(user);

        User result = userService.getById(ObjectMock.ID);

        verify(userRepository)
                .findOne(ObjectMock.ID);

        assertThat(result)
                .isEqualTo(user);
    }

    @Test
    public void shouldGetAllUsers() {
        User user1 = new User();
        User user2 = new User();
        List<User> users = Arrays.asList(user1, user2);

        when(userRepository.findAll())
                .thenReturn(users);

        List<User> result = userService.getAll();

        verify(userRepository)
                .findAll();

        assertThat(result)
                .isNotEmpty()
                .isEqualTo(users);
        assertThat(result.containsAll(users))
                .isTrue();
        assertThat(result.size())
                .isEqualTo(2);
    }

    @Test
    public void shouldRegister() {
        UserDto userDto = ObjectMock.mockUserDto();
        User user = new User(userDto);
        user.addRole(Role.USER);

        when(userFactory.createUser(userDto))
                .thenReturn(user);
        when(userRepository.save(user))
                .thenReturn(user);

        User result = userService.register(userDto);

        verify(userFactory)
                .createUser(userDto);
        verify(userRepository)
                .save(user);

        assertThat(result)
                .isEqualTo(user);
    }

    @Test
    public void shouldUpdatePassword() {
        User user = new User();

        when(userRepository.getOne(ObjectMock.ID))
                .thenReturn(user);
        when(passwordEncoder.encode(ObjectMock.PASSWORD))
                .thenReturn(ObjectMock.PASSWORD);
        when(userRepository.save(user))
                .thenReturn(user);

        User result = userService.updatePassword(ObjectMock.ID, ObjectMock.PASSWORD);

        verify(passwordEncoder)
                .encode(ObjectMock.PASSWORD);
        verify(userRepository)
                .save(user);

        assertThat(result.getPassword())
                .isNotBlank()
                .isEqualTo(ObjectMock.PASSWORD);
    }

    @Test
    public void shouldUpdateUsername() throws Exception {
        User user = new User();

        when(userRepository.getOne(ObjectMock.ID))
                .thenReturn(user);
        when(userRepository.save(user))
                .thenReturn(user);

        User result = userService.updateUsername(ObjectMock.ID, ObjectMock.USERNAME);

        verify(userRepository)
                .save(user);

        assertThat(result.getUsername())
                .isNotBlank()
                .isEqualTo(ObjectMock.USERNAME);
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowEntityNotFoundExceptionWhenUserNotExists() {
        when(userRepository.exists(ObjectMock.ID))
                .thenReturn(false);

        userService.getById(ObjectMock.ID);
    }

    @Test
    public void shouldReturnUserByIdWhenExists() {
        User user = ObjectMock.mockUser();

        when(userRepository.findOne(user.getId()))
                .thenReturn(user);

        User result = userService.getById(user.getId());

        verify(userRepository)
                .findOne(user.getId());

        assertThat(result)
                .isEqualTo(user);
    }
}
