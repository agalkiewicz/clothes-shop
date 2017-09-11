package com.example.zzjp.clothesShop.service;

import com.example.zzjp.clothesShop.dto.FilterDto;
import com.example.zzjp.clothesShop.model.*;
import com.example.zzjp.clothesShop.dto.ItemDto;
import com.example.zzjp.clothesShop.repository.DiscountRepository;
import com.example.zzjp.clothesShop.repository.ItemRepository;
import com.example.zzjp.clothesShop.repository.ItemStateRepository;
import com.example.zzjp.clothesShop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;

    private final CategoryService categoryService;

    private final ItemStateRepository itemStateRepository;

    private final DiscountRepository discountRepository;

    private final OrderRepository orderRepository;

    @Autowired
    public ItemService(final ItemRepository itemRepository,
                       final CategoryService categoryService,
                       final ItemStateRepository itemStateRepository,
                       final DiscountRepository discountRepository,
                       final OrderRepository orderRepository) {
        this.itemRepository = itemRepository;
        this.categoryService = categoryService;
        this.itemStateRepository = itemStateRepository;
        this.discountRepository = discountRepository;
        this.orderRepository = orderRepository;
    }

    public List<Item> getAll() {
        return itemRepository.findAll();
    }

    public Item getById(Long id) {
        return itemRepository.findOne(id);
    }

    public Item getByName(String name) {
        return itemRepository.findByName(name);
    }

    public List<Item> getByCategoryId(long id) {
        return itemRepository.findByCategoryId(id);
    }

    public List<Item> getByPrice(BigDecimal price) {
        return itemRepository.findByPrice(price);
    }

    public List<Item> getByPriceGreaterThan(BigDecimal price) {
        return itemRepository.findByPriceGreaterThan(price);
    }

    public List<Item> getByPriceBetween(BigDecimal price1, BigDecimal price2) {
        return itemRepository.findByPriceBetween(price1, price2);
    }

    public List<Item> getByPriceLessThan(BigDecimal price) {
        return itemRepository.findByPriceLessThan(price);
    }

    public List<Item> getByColor(String color) {
        return itemRepository.findByColor(color);
    }

    public List<Item> getBySize(Size size) {
        return itemRepository.findBySize(size);
    }

    public Item add(ItemDto itemDto) {
        Category category = categoryService.getById(itemDto.getCategoryId());
        Item item = new Item(itemDto);
        item.setCategory(category);

        return itemRepository.save(item);
    }

    public Item update(Long id, ItemDto itemDto) {
        Item item = itemRepository.findOne(id);
        Category category = categoryService.getById(itemDto.getCategoryId());

        item.setName(itemDto.getName());
        item.setColor(itemDto.getColor());
        item.setPrice(itemDto.getPrice());
        item.setSize(itemDto.getSize());
        item.setCategory(category);

        return itemRepository.saveAndFlush(item);
    }

    public void remove(Long id) {
//        ItemState itemState = itemStateRepository.findByItemId(id);
//        itemStateRepository.delete(itemState);
//        List<Discount> discounts = discountRepository.findByItemId(id);
//        ArrayList<Order> orders = new ArrayList<>();
//        for (Discount discount : discounts) {
//            List<Order> ordersLocal = orderRepository.findByDiscount(discount);
//            orders.addAll(ordersLocal);
//        }
//        for (Order order : orders) {
//            order.setDiscount(null);
//        }
//        discountRepository.delete(discounts);
//
//        Item item = itemRepository.findOne(id);
//        itemRepository.delete(id);
//        List<Order> itemOrders = orderRepository.findByItemsIn(item);
//        for (Order itemOrder : itemOrders) {
//            itemOrder.removeItem(id);
//            orderRepository.saveAndFlush(itemOrder);
//        }
//
    }

    public List<Item> filter(FilterDto filterDto) {
        Long categoryId = filterDto.getCategoryId();
        String color = filterDto.getColor();
        Size size = filterDto.getSize();
        BigDecimal minPrice = filterDto.getPriceMin();
        BigDecimal maxPrice = filterDto.getPriceMax();

        if (categoryId != null) {
            if (color != null) {
                if (size != null) {
                    return itemRepository.findByCategoryIdAndColorAndSizeAndPriceBetween(
                            categoryId,
                            color,
                            size,
                            minPrice,
                            maxPrice
                    );
                } else {
                    return itemRepository.findByCategoryIdAndColorAndPriceBetween(
                            categoryId,
                            color,
                            minPrice,
                            maxPrice
                    );
                }
            } else if (size != null) {
                return itemRepository.findByCategoryIdAndSizeAndPriceBetween(
                        categoryId,
                        size,
                        minPrice,
                        maxPrice
                );
            } else {
                return itemRepository.findByCategoryIdAndPriceBetween(
                        categoryId,
                        minPrice,
                        maxPrice
                );
            }
        } else if (color != null) {
            if (size != null) {
                return itemRepository.findByColorAndSizeAndPriceBetween(
                        color,
                        size,
                        minPrice,
                        maxPrice
                );
            } else {
                return itemRepository.findByColorAndPriceBetween(
                        color,
                        minPrice,
                        maxPrice
                );
            }
        } else if (size != null) {
            return itemRepository.findBySizeAndPriceBetween(
                    size,
                    minPrice,
                    maxPrice
            );
        } else {
            return itemRepository.findByPriceBetween(
                    minPrice,
                    maxPrice
            );
        }
    }
}