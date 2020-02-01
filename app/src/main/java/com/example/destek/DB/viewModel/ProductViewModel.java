package com.example.destek.DB.viewModel;

import android.app.Application;

import com.example.destek.DB.dao.ProductDao;
import com.example.destek.DB.repository.ProductRepo;
import com.example.destek.DB.tabels.Product;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


public class ProductViewModel extends AndroidViewModel implements ProductDao {

    ProductRepo mProductRepo;

    public ProductViewModel(Application application) {
        super(application);
        mProductRepo = new ProductRepo(application);

    }


    @Override
    public LiveData<List<Product>> getAllItem() {
        return mProductRepo.getAllItem();
    }

    @Override
    public LiveData<Product> findItemById(int Product_Id) {
        return mProductRepo.findItemById(Product_Id);
    }

    @Override
    public LiveData<List<Product>> findItemByCatId(int id) {
        return mProductRepo.findItemByCatId(id);
    }


    @Override
    public int itemCount() {
        return mProductRepo.itemCount();
    }

    @Override
    public void insertItem(final Product Product) {
        mProductRepo.insertItem(Product);
    }

    @Override
    public int deleteItem(final Product Product) {

        return mProductRepo.deleteItem(Product);

    }

    @Override
    public int deleteAllItem() {
        return mProductRepo.deleteAllItem();
    }

}
