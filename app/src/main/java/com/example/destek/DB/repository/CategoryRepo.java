package com.example.destek.DB.repository;

import android.app.Application;
import android.os.Handler;
import android.util.Log;

import com.example.destek.BaseResponse;
import com.example.destek.DB.dao.CategoryDao;
import com.example.destek.DB.dao.ProductDao;
import com.example.destek.DB.database.AppDatabase;
import com.example.destek.DB.tabels.Category;
import com.example.destek.DB.tabels.Product;
import com.example.destek.network.RestClient;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import retrofit2.Call;
import retrofit2.Callback;


@Singleton
public class CategoryRepo implements CategoryDao {

    private final String TAG = CategoryRepo.class.getSimpleName();

    private final CategoryDao categoryDao;
    private final ProductDao productDao;
    Application application;

    @Inject
    public CategoryRepo(Application application) {
        this.categoryDao = AppDatabase.getAppDatabase(application).getCategoryDao();
        this.productDao = AppDatabase.getAppDatabase(application).getProductDao();
        this.application = application;
    }


    @Override
    public LiveData<List<Category>> getAllItem() {
        getCategoriesFromServer();
        return categoryDao.getAllItem();
    }

    @Override
    public Category findItemById(int id) {
        return categoryDao.findItemById(id);
    }

    @Override
    public int itemCount() {
        return categoryDao.itemCount();
    }

    @Override
    public void insertItem(final Category category) {
        Handler mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                categoryDao.insertItem(category);
                for (int i = 0; i < category.getProducts().size(); i++) {
                    Product mProduct=category.getProducts().get(i);
                    mProduct.setCategoryId(category.getId());
                    productDao.insertItem(mProduct);
                }
            }
        });
    }

    @Override
    public int deleteItem(final Category category) {

        Handler mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                categoryDao.deleteItem(category);
            }
        });
        return 0;
    }

    @Override
    public int deleteAllItem() {
        return categoryDao.deleteAllItem();
    }

    private void getCategoriesFromServer() {

        Call<BaseResponse> call = RestClient.getApiService().getCategories();
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, retrofit2.Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        String json = new Gson().toJson(response.body().getCategories());
                        Log.i(TAG, "onResponse: " + json);
                        List<Category> mItemList = new Gson().fromJson(json, new TypeToken<List<Category>>() {
                        }.getType());
                        if (mItemList != null && mItemList.size() > 0) {
                            for (int i = 0; i < mItemList.size(); i++) {
                                insertItem(mItemList.get(i));
                            }
                        }
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

                if (t instanceof ConnectException) {
                    Log.e(TAG, "Error ConnectException");
                } else if (t instanceof SocketTimeoutException) {
                    Log.e(TAG, "Error SocketTimeoutException");
                    getCategoriesFromServer();
                } else if (t instanceof JsonSyntaxException) {
                    Log.e(TAG, "Error JsonSyntaxException");
                } else {
                    Log.e(TAG, "Error :", t);
                }
            }
        });
    }

}
