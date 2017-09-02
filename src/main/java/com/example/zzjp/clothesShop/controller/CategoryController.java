package com.example.zzjp.clothesShop.controller;

import com.example.zzjp.clothesShop.model.Category;
import com.example.zzjp.clothesShop.model.CategoryDto;
import com.example.zzjp.clothesShop.model.Item;
import com.example.zzjp.clothesShop.service.CategoryService;
import com.example.zzjp.clothesShop.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    private final ItemService itemService;

    @Autowired
    public CategoryController(CategoryService categoryService, ItemService itemService) {
        this.categoryService = categoryService;
        this.itemService = itemService;
    }

    @GetMapping("/{id}")
    ResponseEntity<Category> getById(@PathVariable("id") Long id) {
        try {
            Category category = categoryService.getById(id);

            return new ResponseEntity<>(category, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{name}")
    ResponseEntity<Category> getByName(@PathVariable("name") String name) {
        try {
            Category category = categoryService.getByName(name);

            return new ResponseEntity<>(category, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    ResponseEntity<List<Category>> getAll() {
        try {
            List<Category> categories = categoryService.getAll();

            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    ResponseEntity<Category> add(@RequestBody @Valid CategoryDto categoryDto) {
        try {
            Category category = categoryService.add(categoryDto);

            return new ResponseEntity<>(category, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<Category> update(@PathVariable("id") Long id, @RequestBody @Valid CategoryDto categoryDto) {
        try {
            Category category = categoryService.update(id, categoryDto.getName());

            return new ResponseEntity<>(category, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity remove(@PathVariable("id") Long id) {
        try {
            categoryService.remove(id);

            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/items")
    ResponseEntity<List<Item>> getItemsByCategory(@PathVariable("id") Long id) {
        try {
            List<Item> items = itemService.getByCategoryId(id);

            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
