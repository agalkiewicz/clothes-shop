package com.example.zzjp.clothesShop.service;

import com.example.zzjp.clothesShop.model.Category;
import com.example.zzjp.clothesShop.model.Item;
import com.example.zzjp.clothesShop.dto.ItemDto;
import com.example.zzjp.clothesShop.model.Size;
import com.example.zzjp.clothesShop.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;

    private final CategoryService categoryService;

    @Autowired
    public ItemService(final ItemRepository itemRepository,
                       final CategoryService categoryService) {
        this.itemRepository = itemRepository;
        this.categoryService = categoryService;
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

        return itemRepository.save(item);
    }

    public void remove(Long id) {
        itemRepository.delete(id);
    }
}