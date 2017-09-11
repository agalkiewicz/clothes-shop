package com.example.zzjp.clothesShop.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class ItemState {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //    @OneToOne(mappedBy = "itemState")
//    @OneToOne()
//    @JoinColumn(name = "item_id", referencedColumnName = "id")
//    private Item item;

    @NotNull
    @Min(0)
    private Integer amount;

//    public Item getItem() {
//        return item;
//    }
//
//    public void setItem(Item item) {
//        this.item = item;
//    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        return "ItemState{" +
                "id=" + id +
                ", amount=" + amount +
                '}';
    }
}
