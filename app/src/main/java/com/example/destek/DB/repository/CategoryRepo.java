package com.example.destek.DB.repository;

import android.app.Application;
import android.os.Handler;
import android.util.Log;

import com.example.destek.BaseResponse;
import com.example.destek.DB.dao.CategoryDao;
import com.example.destek.DB.dao.ProductDao;
import com.example.destek.DB.dao.TaxDao;
import com.example.destek.DB.dao.VariantDao;
import com.example.destek.DB.database.AppDatabase;
import com.example.destek.DB.tabels.Category;
import com.example.destek.DB.tabels.Product;
import com.example.destek.DB.tabels.Ranking;
import com.example.destek.DB.tabels.Tax;
import com.example.destek.DB.tabels.Variant;
import com.example.destek.network.RestClient;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Calendar;
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
    private final VariantDao variantDao;
    private final TaxDao taxDao;
    Application application;

    @Inject
    public CategoryRepo(Application application) {
        this.categoryDao = AppDatabase.getAppDatabase(application).getCategoryDao();
        this.productDao = AppDatabase.getAppDatabase(application).getProductDao();
        this.taxDao = AppDatabase.getAppDatabase(application).getTaxDao();
        this.variantDao = AppDatabase.getAppDatabase(application).getVariantDao();
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

                    Tax mTax=mProduct.getTax();
                    mTax.setId(Calendar.getInstance().getTimeInMillis());
                    mTax.setProductId(mProduct.getId());
                    taxDao.insertItem(mTax);

                    for (int j = 0; j < mProduct.getVariants().size(); j++) {
                        Variant mVariant=mProduct.getVariants().get(j);
                        mVariant.setProductId(mProduct.getId());
                        variantDao.insertItem(mVariant);
                    }
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
                        String jsonCat = new Gson().toJson(response.body().getCategories());
                        String jsonRank = new Gson().toJson(response.body().getRankings());
                        Log.i(TAG, "onResponse: " + jsonCat);
                        List<Category> mItemList = new Gson().fromJson(jsonCat, new TypeToken<List<Category>>() {
                        }.getType());
                        if (mItemList != null && mItemList.size() > 0) {
                            for (int i = 0; i < mItemList.size(); i++) {
                                insertItem(mItemList.get(i));
                            }
                        }

                        List<Ranking> mRankingsList = new Gson().fromJson(jsonRank, new TypeToken<List<Ranking>>() {
                        }.getType());
                        if (mRankingsList != null && mRankingsList.size() > 0) {
                            for (int i = 0; i < mRankingsList.size(); i++) {
                                List<Product> mProducts=mRankingsList.get(i).getProducts();
                                for (int j = 0; j < mProducts.size(); j++) {
                                    if (i==0){
                                        productDao.setViewsCount(mProducts.get(j).getId(),mProducts.get(j).getView_count());
                                    }else if (i==1){
                                        productDao.setOrderCount(mProducts.get(j).getId(),mProducts.get(j).getOrder_count());
                                    }else if (i==2){
                                        productDao.setShareCount(mProducts.get(j).getId(),mProducts.get(j).getShares());
                                    }
                                }
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
