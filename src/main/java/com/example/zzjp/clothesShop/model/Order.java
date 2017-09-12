package com.example.zzjp.clothesShop.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
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

    @ManyToOne()
    @JoinColumn(name = "discount_id", referencedColumnName = "id")
    private Discount discount;

    private boolean isPaidUp;

    private OrderStatus orderStatus;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="delivery_id", referencedColumnName = "id")
    private Delivery delivery;

    public Order(User user) {
        this.user = user;
        this.items = new ArrayList<>();
        this.value = new BigDecimal("0.00");
        this.isPaidUp = false;
    }

    public Order() {
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
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

    public Discount getDiscount() {
        return discount;
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

    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user=" + user +
                ", items=" + items +
                ", value=" + value +
                ", discount=" + discount +
                ", isPaidUp=" + isPaidUp +
                ", orderStatus=" + orderStatus +
                ", delivery=" + delivery +
                '}';
    }
}
