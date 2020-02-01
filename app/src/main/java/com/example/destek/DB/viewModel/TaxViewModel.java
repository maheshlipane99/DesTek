package com.example.destek.DB.viewModel;

import android.app.Application;

import com.example.destek.DB.dao.TaxDao;
import com.example.destek.DB.repository.TaxRepo;
import com.example.destek.DB.tabels.Tax;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


public class TaxViewModel extends AndroidViewModel implements TaxDao {

    TaxRepo mTaxRepo;

    public TaxViewModel(Application application) {
        super(application);
        mTaxRepo = new TaxRepo(application);

    }


    @Override
    public LiveData<List<Tax>> getAllItem() {
        return mTaxRepo.getAllItem();
    }

    @Override
    public Tax findItemById(int Tax_Id) {
        return mTaxRepo.findItemById(Tax_Id);
    }

    @Override
    public LiveData<Tax> findItemByProductId(int id) {
        return mTaxRepo.findItemByProductId(id);
    }


    @Override
    public int itemCount() {
        return mTaxRepo.itemCount();
    }

    @Override
    public void insertItem(final Tax tax) {
        mTaxRepo.insertItem(tax);
    }

    @Override
    public int deleteItem(final Tax tax) {

        return mTaxRepo.deleteItem(tax);

    }

    @Override
    public int deleteAllItem() {
        return mTaxRepo.deleteAllItem();
    }

}
