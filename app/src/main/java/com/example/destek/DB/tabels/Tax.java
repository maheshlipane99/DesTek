package com.example.destek.DB.tabels;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Tax {

    @PrimaryKey
    long id;
    String name;
    double value;
    int ProductId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }
}
