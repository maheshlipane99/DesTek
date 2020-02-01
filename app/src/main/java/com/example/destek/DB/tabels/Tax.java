package com.example.destek.DB.tabels;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Tax {

    @PrimaryKey
    int id;
    String name;
    double value;
    int ProductId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
