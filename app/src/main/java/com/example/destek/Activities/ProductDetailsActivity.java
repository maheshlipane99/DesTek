package com.example.destek.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.destek.Adapter.VariantAdapter;
import com.example.destek.DB.tabels.Product;
import com.example.destek.DB.tabels.Tax;
import com.example.destek.DB.tabels.Variant;
import com.example.destek.DB.viewModel.ProductViewModel;
import com.example.destek.DB.viewModel.TaxViewModel;
import com.example.destek.DB.viewModel.VariantViewModel;
import com.example.destek.Interface.OnRecyclerViewClick;
import com.example.destek.R;
import com.example.destek.Utils.Const;
import com.google.gson.Gson;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class ProductDetailsActivity extends AppCompatActivity implements OnRecyclerViewClick {
    public RecyclerView recyclerView;
    public LinearLayout layoutData;
    private String TAG = ProductDetailsActivity.class.getSimpleName();
    public ProgressBar progressBar;
    private TextView textMessage;
    private TextView textDetails;
    private TextView textTax;
    int mProductId;
    ProductViewModel productViewModel;
    VariantViewModel variantViewModel;
    TaxViewModel taxViewModel;

    private VariantAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setActionBarName(getIntent().getStringExtra(Const.EXTRA_PRODUCT_TITLE));
        mProductId = getIntent().getIntExtra(Const.EXTRA_PRODUCT_ID, 0);
        initialize();
    }

    public void setActionBarName(String tiltle) {
        getSupportActionBar().setTitle(tiltle);
    }

    public void initialize() {
        textDetails = findViewById(R.id.textDetails);
        textTax = findViewById(R.id.textTax);
        recyclerView = findViewById(R.id.recyclerView);
        layoutData = findViewById(R.id.layoutData);
        textMessage = findViewById(R.id.textMessage);
        progressBar = findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mAdapter = new VariantAdapter(this, this);
        recyclerView.setAdapter(mAdapter);
        getData();
        getVariant();

        taxViewModel = ViewModelProviders.of(this).get(TaxViewModel.class);
        taxViewModel.getAllItem().observe(this, new Observer<List<Tax>>() {
            @Override
            public void onChanged(List<Tax> taxes) {
                Log.i(TAG, "onChanged: tax "+new Gson().toJson(taxes));
            }
        });
        taxViewModel.insertItem(new Tax());
    }

    private void getData() {
        showProgressBar();
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productViewModel.findItemById(mProductId).observe(this, new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                if (product != null) {
                    Log.i(TAG, "onChanged: Product "+new Gson().toJson(product));
                    StringBuilder mStringBuilder = new StringBuilder();
                    mStringBuilder.append(product.getName() + "\n");
                    mStringBuilder.append("Added on : " + product.getDate_added());
                    textDetails.setText(mStringBuilder);

                    getTax();
                    showDataView();
                }else {
                    showNoData();
                }
            }
        });

    }

    private void getVariant() {
        Log.i(TAG, "getVariant: Id "+mProductId);
        variantViewModel = ViewModelProviders.of(this).get(VariantViewModel.class);
        variantViewModel.getAllItem().observe(this, new Observer<List<Variant>>() {
            @Override
            public void onChanged(List<Variant> variants) {
                mAdapter.removeAll();
                if (variants != null && variants.size() > 0) {
                    Log.i(TAG, "onChanged: variants "+new Gson().toJson(variants));
                    Log.i(TAG, "onChanged: 1" + variants.size());
                    for (int i = 0; i < variants.size(); i++) {
                        mAdapter.addItem(variants.get(i));
                    }
                    showDataView();
                }
            }
        });
    }

    private void getTax() {
        taxViewModel = ViewModelProviders.of(this).get(TaxViewModel.class);
        taxViewModel.findItemByProductId(mProductId).observe(this, new Observer<Tax>() {
            @Override
            public void onChanged(Tax tax) {
                if (tax != null) {
                    Log.i(TAG, "onChanged: tax "+new Gson().toJson(tax));
                    StringBuilder mStringBuilder = new StringBuilder();
                    mStringBuilder.append(tax.getName() + " : " + tax.getValue());
                    textTax.setText(mStringBuilder);
                    showDataView();
                }
            }
        });
    }

    public void showDataView() {
        progressBar.setVisibility(View.GONE);
        textMessage.setVisibility(View.GONE);
        layoutData.setVisibility(View.VISIBLE);
    }

    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        layoutData.setVisibility(View.GONE);
    }

    public void showNoData() {
        layoutData.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        textMessage.setVisibility(View.VISIBLE);
        textMessage.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_sad_emoji, 0, 0);
        textMessage.setText("Sorry..! No data found");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home: {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClick(View view, int position) {

    }
}
