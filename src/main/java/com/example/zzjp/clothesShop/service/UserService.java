package com.example.zzjp.clothesShop.service;

import com.example.zzjp.clothesShop.model.Order;
import com.example.zzjp.clothesShop.model.Role;
import com.example.zzjp.clothesShop.model.User;
import com.example.zzjp.clothesShop.dto.UserDto;
import com.example.zzjp.clothesShop.repository.OrderRepository;
import com.example.zzjp.clothesShop.repository.UserRepository;
import com.example.zzjp.clothesShop.util.UserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserFactory userFactory;

    private final OrderRepository orderRepository;

    public UserService(final UserRepository userRepository,
                       final PasswordEncoder passwordEncoder,
                       final UserFactory userFactory,
                       final OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userFactory = userFactory;
        this.orderRepository = orderRepository;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(long id) {
        User user = userRepository.findOne(id);

        if (user == null) {
            throw new EntityNotFoundException();
        }

        return user;
    }

    public User register(UserDto userDto) {
        User user = userFactory.createUser(userDto);
        user.addRole(Role.USER);
        return userRepository.save(user);
    }

    public User addAdmin(UserDto userDto) {
        User user = userFactory.createUser(userDto);
        user.addRole(Role.ADMIN);

        return userRepository.save(user);
    }

    public void remove(long id) {
        List<Order> orders = orderRepository.findByUserId(id);
        for (Order order : orders) {
            orderRepository.delete(order);
        }
        userRepository.delete(id);
    }

    public User updatePassword(long id, String password) {
        User user = userRepository.getOne(id);
        String newPassword = passwordEncoder.encode(password);
        user.setPassword(newPassword);

        return userRepository.save(user);
    }

    public User updateUsername(long id, String username) {
        User user = userRepository.getOne(id);
        user.setUsername(username);

        return userRepository.save(user);
    }

    public User getByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("Username: %s not found.", username));
        }

        return user;
    }
}
