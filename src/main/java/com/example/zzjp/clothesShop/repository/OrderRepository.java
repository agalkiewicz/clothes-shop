package com.example.zzjp.clothesShop.repository;

import com.example.zzjp.clothesShop.model.Discount;
import com.example.zzjp.clothesShop.model.Item;
import com.example.zzjp.clothesShop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByDiscount(Discount discount);

    List<Order> findByItemsIn(Item item);

    List<Order> findByUserId(Long userId);
}
