package com.example.zzjp.clothesShop.model;

import com.example.zzjp.clothesShop.dto.ItemDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    private String color;

    private Size size;

    @NotNull
    private BigDecimal price;

    //    @OneToOne(mappedBy = "item")
    @OneToOne()
    @JoinColumn(name = "item_state_id", referencedColumnName = "id")
    private ItemState itemState;

//    @OneToOne()
//    @JoinColumn(name = "item_id", referencedColumnName = "id")
//    private Item item;

    public Item() {
    }

    public Item(ItemDto itemDto) {
        this.name = itemDto.getName();
        this.color = itemDto.getColor();
        this.price = itemDto.getPrice();
        this.size = itemDto.getSize();
    }

    //    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "item_state_id", referencedColumnName = "id")
    public ItemState getItemState() {
        return itemState;
    }

    public void setItemState(ItemState itemState) {
        this.itemState = itemState;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", color='" + color + '\'' +
                ", size=" + size +
                ", price=" + price +
                '}';
    }
}
