package com.example.destek.DB.repository;

import android.app.Application;
import android.os.Handler;
import android.util.Log;

import com.example.destek.DB.dao.TaxDao;
import com.example.destek.DB.database.AppDatabase;
import com.example.destek.DB.tabels.Tax;
import com.example.destek.DB.tabels.Tax;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;


@Singleton
public class TaxRepo implements TaxDao {

    private final String TAG = TaxRepo.class.getSimpleName();

    private final TaxDao taxDao;
    Application application;

    @Inject
    public TaxRepo(Application application) {
        this.taxDao = AppDatabase.getAppDatabase(application).getTaxDao();
        this.application = application;
    }


    @Override
        public LiveData<List<Tax>> getAllItem() {
        return taxDao.getAllItem();
    }

    @Override
    public Tax findItemById(int id) {
        return taxDao.findItemById(id);
    }

    @Override
    public LiveData<Tax> findItemByProductId(int id) {
        return taxDao.findItemByProductId(id);
    }

    @Override
    public int itemCount() {
        return taxDao.itemCount();
    }

    @Override
    public void insertItem(final Tax tax) {
        Handler mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {

                taxDao.insertItem(tax);
            }
        });
    }

    @Override
    public int deleteItem(final Tax tax) {

        Handler mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                taxDao.deleteItem(tax);
            }
        });
        return 0;
    }

    @Override
    public int deleteAllItem() {
        return taxDao.deleteAllItem();
    }

}
