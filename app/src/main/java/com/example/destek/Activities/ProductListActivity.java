package com.example.destek.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.example.destek.Adapter.CategoryAdapter;
import com.example.destek.Adapter.ProductAdapter;
import com.example.destek.DB.tabels.Category;
import com.example.destek.DB.tabels.Product;
import com.example.destek.DB.viewModel.CategoryViewModel;
import com.example.destek.DB.viewModel.ProductViewModel;
import com.example.destek.Interface.OnRecyclerViewClick;
import com.example.destek.R;
import com.example.destek.Utils.Const;
import com.google.gson.Gson;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


public class ProductListActivity extends BasicActivity implements OnRecyclerViewClick, View.OnClickListener {

    private ProductAdapter mAdapter;
    private String TAG = ProductListActivity.class.getSimpleName();
    ProductViewModel productViewModel;
    int mCategoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCategoryId = getIntent().getIntExtra(Const.EXTRA_CATEGORY_ID,0);
        setActionBarName(getIntent().getStringExtra(Const.EXTRA_CATEGORY_TITLE));
        Log.i(TAG, "onCreate: Id "+mCategoryId);
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mAdapter.getFilter().filter(editSearch.getText().toString());
            }
        });

        mAdapter = new ProductAdapter(this, this);
        recyclerView.setAdapter(mAdapter);

        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productViewModel.findItemByCatId(mCategoryId).observe(ProductListActivity.this, new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable List<Product> mProductList) {
                showProgressBar();
                mAdapter.removeAll();
                if (mProductList != null && mProductList.size() > 0) {
                    Log.i(TAG, "onChanged: 1" + mProductList.size());
                    for (int i = 0; i < mProductList.size(); i++) {
                        mAdapter.addItem(mProductList.get(i));
                    }
                    showRecyclerView();
                }else {
                    showNoData();
                }

            }
        });

    }


    @Override
    public void onClick(View view) {


    }

    @Override
    public void onItemClick(View view, int position) {
        Log.i(TAG, "onItemClick: ");
        switch (view.getId()) {
            case R.id.rootView: {
                startActivity(new Intent(ProductListActivity.this, ProductDetailsActivity.class)
                        .putExtra(Const.EXTRA_PRODUCT_ID, mAdapter.getItem(position).getId())
                        .putExtra(Const.EXTRA_PRODUCT_TITLE, mAdapter.getItem(position).getName())
                );

            }
        }

    }
}
