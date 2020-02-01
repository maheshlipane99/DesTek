package com.example.destek;

import com.example.destek.DB.tabels.Category;

import java.util.List;

public class BaseResponse {

    List<Category> categories;
   // List<Category> rankings;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
