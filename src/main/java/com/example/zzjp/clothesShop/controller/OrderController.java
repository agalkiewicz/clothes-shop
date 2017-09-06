package com.example.zzjp.clothesShop.controller;

import com.example.zzjp.clothesShop.dto.AddDiscountDto;
import com.example.zzjp.clothesShop.dto.UpdateOrderStatusDto;
import com.example.zzjp.clothesShop.exceptions.NoItemException;
import com.example.zzjp.clothesShop.model.Order;
import com.example.zzjp.clothesShop.model.User;
import com.example.zzjp.clothesShop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "api/v1/orders")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(produces = "application/json")
    ResponseEntity<Order> add() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();
            Order order = orderService.add(user);

            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/{orderId}/items", produces = "application/json")
    @PreAuthorize("@permissionChecker.hasAuthorityToModifyOrder(principal, #orderId) ")
    ResponseEntity<Order> addItem(@PathVariable Long orderId, @RequestBody @Valid Long itemId) {
        try {
            Order order = orderService.addItem(orderId, itemId);

            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (NoItemException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{orderId}/items/{itemId}", produces = "application/json")
    @PreAuthorize("@permissionChecker.hasAuthorityToModifyOrder(principal, #orderId) ")
    ResponseEntity removeItem(@PathVariable Long orderId, @PathVariable Long itemId) {
        try {
            orderService.removeItem(orderId, itemId);

            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/{orderId}/discounts", produces = "application/json")
    @PreAuthorize("@permissionChecker.hasAuthorityToModifyOrder(principal, #orderId) ")
    ResponseEntity<Order> addDiscount(@PathVariable Long orderId, @RequestBody @Valid AddDiscountDto addDiscountDto) {
        try {
            Order order = orderService.setDiscount(orderId, addDiscountDto.getDiscountId());

            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{orderId}/delivery", produces = "application/json")
    @PreAuthorize("@permissionChecker.hasAuthorityToModifyOrder(principal, #orderId) ")
    ResponseEntity<Order> setDelivery(@PathVariable Long orderId, @RequestBody @Valid Long deliveryId) {
        try {
            Order order = orderService.setDelivery(orderId, deliveryId);

            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{orderId}", produces = "application/json")
    @PreAuthorize("@permissionChecker.hasAuthorityToModifyOrder(principal, #orderId) ")
    ResponseEntity remove(@PathVariable Long orderId) {
        try {
            orderService.remove(orderId);

            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{orderId}/payment", produces = "application/json")
    ResponseEntity<Order> pay(@PathVariable Long orderId) {
        try {
            Order order = orderService.pay(orderId);

            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{orderId}/status", produces = "application/json")
    @PreAuthorize("@permissionChecker.hasAuthorityToModifyOrder(principal, #orderId) ")
    ResponseEntity<Order> updateStatus(@PathVariable Long orderId, @RequestBody @Valid UpdateOrderStatusDto updateOrderStatusDto) {
        try {
            Order order = orderService.updateStatus(orderId, updateOrderStatusDto.getOrderStatus());

            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{orderId}/value", produces = "application/json")
    ResponseEntity<Order> countValue(@PathVariable Long orderId) {
        try {
            Order order = orderService.countValue(orderId);

            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
