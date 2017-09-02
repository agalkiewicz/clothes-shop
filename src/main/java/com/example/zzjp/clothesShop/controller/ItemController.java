package com.example.zzjp.clothesShop.controller;

import com.example.zzjp.clothesShop.model.*;
import com.example.zzjp.clothesShop.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("api/v1/items")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/{id}")
    ResponseEntity<Item> getById(@PathVariable("id") Long id) {
        try {
            Item item = itemService.getById(id);

            return new ResponseEntity<>(item, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{name}")
    ResponseEntity<Item> getByName(@PathVariable("name") String name) {
        try {
            Item item = itemService.getByName(name);

            return new ResponseEntity<>(item, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{price}")
    ResponseEntity<List<Item>> getByPrice(@PathVariable("price") BigDecimal price) {
        try {
            List<Item> items = itemService.getByPrice(price);

            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{color}")
    ResponseEntity<List<Item>> getByColor(@PathVariable("color") String color) {
        try {
            List<Item> items = itemService.getByColor(color);

            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{size}")
    ResponseEntity<List<Item>> getBySize(@PathVariable("size") Size size) {
        try {
            List<Item> items = itemService.getBySize(size);

            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{amount}")
    ResponseEntity<List<Item>> getByPrice(@PathVariable("amount") Integer amount) {
        try {
            List<Item> items = itemService.getByAmount(amount);

            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    ResponseEntity<List<Item>> getAll() {
        try {
            List<Item> items = itemService.getAll();

            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    ResponseEntity<Item> add(@RequestBody @Valid ItemDto itemDto) {
        try {
            Item item = itemService.add(itemDto);

            return new ResponseEntity<>(item, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<Item> update(@PathVariable("id") Long id, @RequestBody @Valid ItemDto itemDto) {
        try {
            Item item = itemService.update(id, itemDto);

            return new ResponseEntity<>(item, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity remove(@PathVariable("id") Long id) {
        try {
            itemService.remove(id);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
