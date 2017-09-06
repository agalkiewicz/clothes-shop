package com.example.zzjp.clothesShop.service;

import com.example.zzjp.clothesShop.model.Category;
import com.example.zzjp.clothesShop.dto.CategoryDto;
import com.example.zzjp.clothesShop.model.Item;
import com.example.zzjp.clothesShop.repository.CategoryRepository;
import com.example.zzjp.clothesShop.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final ItemRepository itemRepository;

    @Autowired
    public CategoryService(final CategoryRepository categoryRepository, ItemRepository itemRepository) {
        this.categoryRepository = categoryRepository;
        this.itemRepository = itemRepository;
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category getById(Long id) {
        return categoryRepository.findOne(id);
    }

    public Category getByName(String name) {
        return categoryRepository.findByName(name);
    }

    public Category add(CategoryDto categoryDto) {
        Category category = new Category(categoryDto);
        return categoryRepository.save(category);
    }

    public Category update(Long id, String name) {
        Category category = categoryRepository.findOne(id);
        category.setName(name);
        return categoryRepository.save(category);
    }

    public void remove(Long id) {
        List<Item> items = itemRepository.findByCategoryId(id);
        for (Item item : items) {
            item.setCategory(null);
        }
        categoryRepository.delete(id);
    }
}
