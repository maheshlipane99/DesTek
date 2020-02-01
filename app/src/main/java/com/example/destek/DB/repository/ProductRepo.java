package com.example.destek.DB.repository;

import android.app.Application;
import android.os.Handler;
import android.util.Log;

import com.example.destek.BaseResponse;
import com.example.destek.DB.dao.ProductDao;
import com.example.destek.DB.dao.TaxDao;
import com.example.destek.DB.dao.VariantDao;
import com.example.destek.DB.database.AppDatabase;
import com.example.destek.DB.tabels.Product;
import com.example.destek.DB.tabels.Product;
import com.example.destek.DB.tabels.Tax;
import com.example.destek.DB.tabels.Variant;
import com.example.destek.Utils.Const;
import com.example.destek.network.RestClient;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import retrofit2.Call;
import retrofit2.Callback;


@Singleton
public class ProductRepo implements ProductDao {

    private final String TAG = ProductRepo.class.getSimpleName();

    private final ProductDao productDao;
    Application application;

    @Inject
    public ProductRepo(Application application) {
        this.productDao = AppDatabase.getAppDatabase(application).getProductDao();
        this.application = application;
    }


    @Override
    public LiveData<List<Product>> getAllItem() {
        return productDao.getAllItem();
    }

    @Override
    public LiveData<Product> findItemById(int id) {
        return productDao.findItemById(id);
    }

    @Override
    public LiveData<List<Product>> findItemByCatId(int id) {
        return productDao.findItemByCatId(id);
    }

    @Override
    public int itemCount() {
        return productDao.itemCount();
    }

    @Override
    public int setShareCount(int id, int shareCount) {
        return productDao.setShareCount(id, shareCount);
    }

    @Override
    public int setOrderCount(int id, int orderCount) {
        return productDao.setOrderCount(id,orderCount);
    }

    @Override
    public int setViewsCount(int id, int viewsCount) {
        return productDao.setViewsCount(id,viewsCount);
    }

    @Override
    public void insertItem(final Product product) {
        Log.i(TAG, "insertItem: " + new Gson().toJson(product));
        Handler mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                productDao.insertItem(product);
            }
        });
    }

    @Override
    public int deleteItem(final Product product) {

        Handler mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                productDao.deleteItem(product);
            }
        });
        return 0;
    }

    @Override
    public int deleteAllItem() {
        return productDao.deleteAllItem();
    }

    @Override
    public LiveData<List<Product>> getMostViewedProduct() {
        return productDao.getMostViewedProduct();
    }

    @Override
    public LiveData<List<Product>> getMostSharedProduct() {
        return productDao.getMostSharedProduct();
    }

    @Override
    public LiveData<List<Product>> getMostOrderedProduct() {
        return productDao.getMostOrderedProduct();
    }

}
