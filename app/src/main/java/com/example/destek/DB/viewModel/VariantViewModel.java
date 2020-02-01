package com.example.destek.DB.viewModel;

import android.app.Application;

import com.example.destek.DB.dao.VariantDao;
import com.example.destek.DB.repository.VariantRepo;
import com.example.destek.DB.tabels.Variant;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


public class VariantViewModel extends AndroidViewModel implements VariantDao {

    VariantRepo mVariantRepo;

    public VariantViewModel(Application application) {
        super(application);
        mVariantRepo = new VariantRepo(application);

    }


    @Override
    public LiveData<List<Variant>> getAllItem() {
        return mVariantRepo.getAllItem();
    }

    @Override
    public Variant findItemById(int Variant_Id) {
        return mVariantRepo.findItemById(Variant_Id);
    }

    @Override
    public LiveData<List<Variant>> findItemByProductId(int id) {
        return mVariantRepo.findItemByProductId(id);
    }


    @Override
    public int itemCount() {
        return mVariantRepo.itemCount();
    }

    @Override
    public void insertItem(final Variant variant) {
        mVariantRepo.insertItem(variant);
    }

    @Override
    public int deleteItem(final Variant variant) {

        return mVariantRepo.deleteItem(variant);

    }

    @Override
    public int deleteAllItem() {
        return mVariantRepo.deleteAllItem();
    }

}
