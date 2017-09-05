package com.example.zzjp.clothesShop.model;

import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ItemState {

    private Item item;

    @NotNull
    @Min(0)
    private Integer amount;

    @OneToOne(mappedBy = "itemState")
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ItemState{" +
                "item=" + item +
                ", amount=" + amount +
                '}';
    }
}
