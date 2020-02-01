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
    private final VariantDao variantDao;
    private final TaxDao taxDao;
    Application application;

    @Inject
    public ProductRepo(Application application) {
        this.productDao = AppDatabase.getAppDatabase(application).getProductDao();
        this.variantDao = AppDatabase.getAppDatabase(application).getVariantDao();
        this.taxDao = AppDatabase.getAppDatabase(application).getTaxDao();
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
    public void insertItem(final Product product) {
        Handler mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                productDao.insertItem(product);
                for (int i = 0; i < product.getVariants().size(); i++) {
                    Variant mVariant=product.getVariants().get(i);
                    mVariant.setProductId(product.getId());
                    variantDao.insertItem(mVariant);
                }
                Tax mTax=product.getTax();
                mTax.setProductId(product.getId());
                taxDao.insertItem(mTax);
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

}
