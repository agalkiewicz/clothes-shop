package com.example.zzjp.clothesShop.controller;

import com.example.zzjp.clothesShop.model.*;
import com.example.zzjp.clothesShop.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("api/v1/items")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    ResponseEntity<Item> getById(@PathVariable("id") Long id) {
        try {
            Item item = itemService.getById(id);

            if(item == null) {
                return new ResponseEntity<>(item, HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(item, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(produces = "application/json")
    ResponseEntity<List<Item>> getAll() {
        try {
            List<Item> items = itemService.getAll();

            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(produces = "application/json")
    ResponseEntity<Item> add(@RequestBody @Valid ItemDto itemDto) {
        try {
            Item item = itemService.add(itemDto);

            return new ResponseEntity<>(item, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    ResponseEntity<Item> update(@PathVariable("id") Long id, @RequestBody @Valid ItemDto itemDto) {
        try {
            Item item = itemService.update(id, itemDto);

            return new ResponseEntity<>(item, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    ResponseEntity remove(@PathVariable("id") Long id) {
        try {
            itemService.remove(id);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}