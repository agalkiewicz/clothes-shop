package com.example.zzjp.clothesShop.repository;

import com.example.zzjp.clothesShop.model.ItemState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemStateRepository extends JpaRepository<ItemState, Long> {

}
