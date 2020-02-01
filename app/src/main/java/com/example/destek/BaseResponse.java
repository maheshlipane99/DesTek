package com.example.destek;

import com.example.destek.DB.tabels.Category;
import com.example.destek.DB.tabels.Ranking;

import java.util.List;

public class BaseResponse {

    List<Category> categories;
    List<Ranking> rankings;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Ranking> getRankings() {
        return rankings;
    }

    public void setRankings(List<Ranking> rankings) {
        this.rankings = rankings;
    }
}
