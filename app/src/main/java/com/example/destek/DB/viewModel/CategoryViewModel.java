package com.example.destek.DB.viewModel;

import android.app.Application;

import com.example.destek.DB.dao.CategoryDao;
import com.example.destek.DB.repository.CategoryRepo;
import com.example.destek.DB.tabels.Category;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


public class CategoryViewModel extends AndroidViewModel implements CategoryDao {

    CategoryRepo mCategoryRepo;

    public CategoryViewModel(Application application) {
        super(application);
        mCategoryRepo = new CategoryRepo(application);

    }


    @Override
    public LiveData<List<Category>> getAllItem() {
        return mCategoryRepo.getAllItem();
    }

    @Override
    public Category findItemById(int Category_Id) {
        return mCategoryRepo.findItemById(Category_Id);
    }

    @Override
    public int itemCount() {
        return mCategoryRepo.itemCount();
    }

    @Override
    public void insertItem(final Category Category) {
        mCategoryRepo.insertItem(Category);
    }

    @Override
    public int deleteItem(final Category Category) {

        return mCategoryRepo.deleteItem(Category);

    }

    @Override
    public int deleteAllItem() {
        return mCategoryRepo.deleteAllItem();
    }

}
