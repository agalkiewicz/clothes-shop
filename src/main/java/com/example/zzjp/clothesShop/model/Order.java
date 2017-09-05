package com.example.zzjp.clothesShop.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToMany()
    @JoinTable(
            name="orders_items",
            joinColumns=@JoinColumn(name="order_id", referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="item_id", referencedColumnName="id"))
    private List<Item> items;

    private BigDecimal value;

    @ManyToMany()
    @JoinTable(
            name="orders_discounts",
            joinColumns=@JoinColumn(name="order_id", referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="discount_id", referencedColumnName="id"))
    private List<Discount> discounts;


    private boolean isPaidUp;

    @NotNull
    private OrderStatus orderStatus;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="delivery_id", referencedColumnName = "id")
    private Delivery delivery;

    public Order(User user) {
        this.user = user;
        this.items = new ArrayList<>();
        this.value = new BigDecimal("0.00");
        this.discounts = new ArrayList<>();
        isPaidUp = false;
        orderStatus = OrderStatus.RECEIVED;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public List<Discount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<Discount> discounts) {
        this.discounts = discounts;
    }

    public boolean isPaidUp() {
        return isPaidUp;
    }

    public void setPaidUp(boolean paidUp) {
        isPaidUp = paidUp;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user=" + user +
                ", items=" + items +
                ", value=" + value +
                ", discounts=" + discounts +
                ", isPaidUp=" + isPaidUp +
                ", orderStatus=" + orderStatus +
                ", delivery=" + delivery +
                '}';
    }
}
