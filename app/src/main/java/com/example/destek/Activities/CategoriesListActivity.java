package com.example.destek.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.example.destek.Adapter.CategoryAdapter;
import com.example.destek.DB.tabels.Category;
import com.example.destek.DB.viewModel.CategoryViewModel;
import com.example.destek.Interface.OnRecyclerViewClick;
import com.example.destek.R;
import com.example.destek.Utils.Const;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


public class CategoriesListActivity extends BasicActivity implements OnRecyclerViewClick, View.OnClickListener {

    private CategoryAdapter mAdapter;
    private String TAG = CategoriesListActivity.class.getSimpleName();
    CategoryViewModel categoryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setActionBarName("Categories");
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

        mAdapter = new CategoryAdapter(this, this);
        recyclerView.setAdapter(mAdapter);

        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        categoryViewModel.getAllItem().observe(CategoriesListActivity.this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> mCategoryList) {

                showProgressBar();
                mAdapter.removeAll();
                if (mCategoryList != null && mCategoryList.size() > 0) {
                    Log.i(TAG, "onChanged: 2" + mCategoryList.size());
                    for (int i = 0; i < mCategoryList.size(); i++) {
                        mAdapter.addItem(mCategoryList.get(i));
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
                startActivity(new Intent(CategoriesListActivity.this, ProductListActivity.class)
                        .putExtra(Const.EXTRA_CATEGORY_ID, mAdapter.getItem(position).getId())
                        .putExtra(Const.EXTRA_CATEGORY_TITLE, mAdapter.getItem(position).getName())
                );
                
            }
        }

    }
}
