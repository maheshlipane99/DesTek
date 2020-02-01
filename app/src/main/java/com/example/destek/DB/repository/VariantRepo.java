package com.example.destek.DB.repository;

import android.app.Application;
import android.os.Handler;

import com.example.destek.DB.dao.VariantDao;
import com.example.destek.DB.database.AppDatabase;
import com.example.destek.DB.tabels.Variant;
import com.example.destek.DB.tabels.Variant;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;


@Singleton
public class VariantRepo implements VariantDao {

    private final String TAG = VariantRepo.class.getSimpleName();

    private final VariantDao variantDao;
    Application application;

    @Inject
    public VariantRepo(Application application) {
        this.variantDao = AppDatabase.getAppDatabase(application).getVariantDao();
        this.application = application;
    }


    @Override
        public LiveData<List<Variant>> getAllItem() {
        return variantDao.getAllItem();
    }

    @Override
    public Variant findItemById(int id) {
        return variantDao.findItemById(id);
    }

    @Override
    public LiveData<List<Variant>> findItemByProductId(int id) {
        return variantDao.findItemByProductId(id);
    }

    @Override
    public int itemCount() {
        return variantDao.itemCount();
    }

    @Override
    public void insertItem(final Variant variant) {
        Handler mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                variantDao.insertItem(variant);
            }
        });
    }

    @Override
    public int deleteItem(final Variant variant) {

        Handler mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                variantDao.deleteItem(variant);
            }
        });
        return 0;
    }

    @Override
    public int deleteAllItem() {
        return variantDao.deleteAllItem();
    }

}
