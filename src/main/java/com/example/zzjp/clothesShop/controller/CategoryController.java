package com.example.zzjp.clothesShop.controller;

import com.example.zzjp.clothesShop.model.Category;
import com.example.zzjp.clothesShop.model.CategoryDto;
import com.example.zzjp.clothesShop.model.Item;
import com.example.zzjp.clothesShop.service.CategoryService;
import com.example.zzjp.clothesShop.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    private final ItemService itemService;

    @Autowired
    public CategoryController(CategoryService categoryService, ItemService itemService) {
        this.categoryService = categoryService;
        this.itemService = itemService;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    ResponseEntity<Category> getById(@PathVariable("id") Long id) {
        try {
            Category category = categoryService.getById(id);

            if (category == null) {
                return new ResponseEntity<>(category, HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(category, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(produces = "application/json")
    ResponseEntity<List<Category>> getAll() {
        try {
            List<Category> categories = categoryService.getAll();

            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(produces = "application/json")
    ResponseEntity<Category> add(@RequestBody @Valid CategoryDto categoryDto) {
        try {
            Category category = categoryService.add(categoryDto);

            if (category == null) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return new ResponseEntity<>(category, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    ResponseEntity<Category> update(@PathVariable("id") Long id, @RequestBody @Valid CategoryDto categoryDto) {
        try {
            Category category = categoryService.update(id, categoryDto.getName());

            return new ResponseEntity<>(category, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    ResponseEntity remove(@PathVariable("id") Long id) {
        try {
            categoryService.remove(id);

            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}/items", produces = "application/json")
    ResponseEntity<List<Item>> getItemsByCategory(@PathVariable("id") Long id) {
        try {
            List<Item> items = itemService.getByCategoryId(id);

            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
