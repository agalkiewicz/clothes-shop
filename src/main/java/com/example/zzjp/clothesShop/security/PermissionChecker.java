package com.example.zzjp.clothesShop.security;

import com.example.zzjp.clothesShop.model.Order;
import com.example.zzjp.clothesShop.model.Role;
import com.example.zzjp.clothesShop.model.User;
import com.example.zzjp.clothesShop.repository.OrderRepository;
import com.example.zzjp.clothesShop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PermissionChecker {

    private final UserRepository userRepository;

    private final OrderRepository orderRepository;

    @Autowired
    public PermissionChecker(final UserRepository userRepository, final OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    public boolean hasAuthorityToModifyUser(final User user, final long userId) {
        User foundUser = userRepository.findByUsername(user.getUsername());

        if (foundUser == null) {
            return false;
        }

        return isAdmin(foundUser) || foundUser.getId() == userId;
    }

    public boolean hasAuthorityToModifyOrder(final User user, final long orderId) {
        User foundUser = userRepository.findByUsername(user.getUsername());
        Order order = orderRepository.findOne(orderId);

        if (foundUser == null) {
            return false;
        }
        if (order == null) {
            return true;
        }

        return isAdmin(foundUser) || foundUser.getId().equals(order.getUser().getId());
    }

    private boolean isAdmin(final User user) {
        return user.getRoles().contains(Role.ADMIN);
    }
}
