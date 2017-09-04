package com.example.zzjp.clothesShop.security;

import com.example.zzjp.clothesShop.model.Role;
import com.example.zzjp.clothesShop.model.User;
import com.example.zzjp.clothesShop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PermissionChecker {

    private final UserRepository userRepository;

    @Autowired
    public PermissionChecker(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean hasAuthorityToModifyUser(final User user, final long userId) {
        User foundUser = userRepository.findByUsername(user.getUsername());

        if (foundUser == null) {
            return false;
        }

        return isAdmin(foundUser) || foundUser.getId() == userId;
    }

    private boolean isAdmin(final User user) {
        return user.getRoles().contains(Role.ADMIN);
    }
}
