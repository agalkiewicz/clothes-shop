package com.example.zzjp.clothesShop.repository;

import com.example.zzjp.clothesShop.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {

    List<Discount> findByItemId(Long itemId);
}
