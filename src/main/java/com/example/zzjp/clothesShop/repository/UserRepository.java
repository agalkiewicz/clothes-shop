package com.example.zzjp.clothesShop.repository;

import com.example.zzjp.clothesShop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
