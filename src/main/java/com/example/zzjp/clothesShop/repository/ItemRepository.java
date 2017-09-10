package com.example.zzjp.clothesShop.repository;

import com.example.zzjp.clothesShop.model.Item;
import com.example.zzjp.clothesShop.model.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByCategoryId(Long id);

    Item findByName(String name);

    List<Item> findByPrice(BigDecimal price);

    List<Item> findByPriceGreaterThan(BigDecimal price);

    List<Item> findByPriceBetween(BigDecimal price1, BigDecimal price2);

    List<Item> findByPriceLessThan(BigDecimal price);

    List<Item> findByColor(String color);

    List<Item> findBySize(Size size);

    List<Item> findByCategoryIdAndColorAndSizeAndPriceBetween(Long categoryId,
                                                              String color,
                                                              Size size,
                                                              BigDecimal priceMin,
                                                              BigDecimal priceMax);

    List<Item> findByCategoryIdAndPriceBetween(Long categoryId,
                                               BigDecimal priceMin,
                                               BigDecimal priceMax);

    List<Item> findByColorAndPriceBetween(String color,
                                          BigDecimal priceMin,
                                          BigDecimal priceMax);

    List<Item> findBySizeAndPriceBetween(Size size,
                                         BigDecimal priceMin,
                                         BigDecimal priceMax);

    List<Item> findByCategoryIdAndColorAndPriceBetween(Long categoryId,
                                                       String color,
                                                       BigDecimal priceMin,
                                                       BigDecimal priceMax);

    List<Item> findByCategoryIdAndSizeAndPriceBetween(Long categoryId,
                                                      Size size,
                                                      BigDecimal priceMin,
                                                      BigDecimal priceMax);

    List<Item> findByColorAndSizeAndPriceBetween(String color,
                                                 Size size,
                                                 BigDecimal priceMin,
                                                 BigDecimal priceMax);
}
