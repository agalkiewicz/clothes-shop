package com.example.zzjp.clothesShop.service;

import com.example.zzjp.clothesShop.exceptions.NoItemException;
import com.example.zzjp.clothesShop.model.*;
import com.example.zzjp.clothesShop.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderService {

    private OrderRepository orderRepository;

    private ItemRepository itemRepository;

    private DiscountRepository discountRepository;

    private DeliveryRepository deliveryRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        ItemRepository itemRepository,
                        DiscountRepository discountRepository,
                        DeliveryRepository deliveryRepository) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.discountRepository = discountRepository;
        this.deliveryRepository = deliveryRepository;
    }

    public Order add(User user) {
        Order order = new Order(user);

        return orderRepository.save(order);
    }

    public Order addItem(Long orderId, Long itemId) throws NoItemException {
        Order order = orderRepository.findOne(orderId);
        Item item = itemRepository.findOne(itemId);
        if (item.getItemState().getAmount() == 0) {
            throw new NoItemException();
        }
        int oldAmount = item.getItemState().getAmount();
        item.getItemState().setAmount(oldAmount - 1);
        order.addItem(item);

        return orderRepository.save(order);
    }

    public Order removeItem(Long orderId, Long itemId) {
        Order order = orderRepository.findOne(orderId);
        order.removeItem(itemId);
        Item item = itemRepository.findOne(itemId);
        item.getItemState().setAmount(item.getItemState().getAmount() - 1);

        return orderRepository.save(order);
    }

    public Order setDiscount(Long orderId, Long discountId) {
        Order order = orderRepository.findOne(orderId);
        Discount discount = discountRepository.findOne(discountId);
        order.setDiscount(discount);

        return orderRepository.save(order);
    }

    public Order setDelivery(Long orderId, Long deliveryId) {
        Order order = orderRepository.findOne(orderId);
        Delivery delivery = deliveryRepository.findOne(deliveryId);
        order.setDelivery(delivery);

        return orderRepository.save(order);
    }

    public void remove(Long id) {
        orderRepository.delete(id);
    }

    public Order pay(Long id) {
        Order order = orderRepository.findOne(id);
        order.setPaidUp(true);

        return orderRepository.save(order);
    }

    public Order updateStatus(Long id, OrderStatus orderStatus) {
        Order order = orderRepository.findOne(id);
        order.setOrderStatus(orderStatus);

        return orderRepository.save(order);
    }

    public Order countValue(Long id) {
        Order order = orderRepository.findOne(id);
        BigDecimal value = new BigDecimal("0.00");
        for (Item item : order.getItems()) {
            value = value.add(item.getPrice());
        }
        if (order.getDelivery() != null) {
            value = value.add(order.getDelivery().getPrice());
        }

        order.setValue(value);

        BigDecimal discountValue = countDiscountValue(order);
        value = value.subtract(discountValue);

        order.setValue(value);

        return orderRepository.save(order);
    }

    private BigDecimal countDiscountValue(Order order) {
        BigDecimal value = new BigDecimal("0.00");

        if (order.getDiscount().getDiscountType() == DiscountType.SALE) {
            for (Item item : order.getItems()) {
                if (item.equals(order.getDiscount().getItem())) {
                    BigDecimal multiply = item.getPrice().multiply(order.getDiscount().getPercentage());
                    value = value.add(multiply);
                }
            }
        } else if (order.getDiscount().getDiscountType() == DiscountType.DELIVERY) {
            value = value.add(order.getDelivery().getPrice());
        } else if (order.getDiscount().getDiscountType() == DiscountType.MONEY) {
            if (order.getValue().compareTo(order.getDiscount().getSumOfMoney()) >= 0) {
                BigDecimal multiply = order.getValue().multiply(order.getDiscount().getPercentage());
                value = value.add(multiply);
            }
        }


        return value;
    }
}
