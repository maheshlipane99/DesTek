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
import com.example.destek.Utils.Common;
import com.example.destek.Utils.Const;
import com.google.gson.Gson;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;


public class ProductDetailsActivity extends AppCompatActivity implements OnRecyclerViewClick {
    @BindView(R.id.recyclerView)
    public RecyclerView recyclerView;

    @BindView(R.id.layoutData)
    public LinearLayout layoutData;
    private String TAG = ProductDetailsActivity.class.getSimpleName();

    @BindView(R.id.progressBar)
    public ProgressBar progressBar;

    @BindView(R.id.textMessage)
    public TextView textMessage;

    @BindView(R.id.textName)
    public TextView textName;

    @BindView(R.id.textTax)
    public TextView textTax;

    @BindView(R.id.textDate)
    TextView textDate;

    @BindView(R.id.textOrder)
    TextView textOrder;

    @BindView(R.id.textShare)
    TextView textShare;

    @BindView(R.id.textViews)
    TextView textViews;

    int mProductId;
    ProductViewModel productViewModel;
    VariantViewModel variantViewModel;
    TaxViewModel taxViewModel;

    private VariantAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        ButterKnife.bind(this);
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
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        mAdapter = new VariantAdapter(this, this);
        recyclerView.setAdapter(mAdapter);
        getData();
    }

    private void getData() {
        showProgressBar();
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productViewModel.findItemById(mProductId).observe(this, new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                if (product != null) {
                    Log.i(TAG, "onChanged: Product " + new Gson().toJson(product));
                    textName.setText(product.getName());
                    textDate.setText(Common.getStringDate(product.getDate_added()));
                    textShare.setText(product.getShares()+" times");
                    textOrder.setText(product.getOrder_count()+" times");
                    textViews.setText(product.getView_count()+" times");
                    getVariant();
                    getTax();
                    showDataView();
                } else {
                    showNoData();
                }
            }
        });

    }

    private void getVariant() {
        Log.i(TAG, "getVariant: Id " + mProductId);
        variantViewModel = ViewModelProviders.of(this).get(VariantViewModel.class);
        variantViewModel.findItemByProductId(mProductId).observe(this, new Observer<List<Variant>>() {
            @Override
            public void onChanged(List<Variant> variants) {
                mAdapter.removeAll();
                if (variants != null && variants.size() > 0) {
                    Log.i(TAG, "onChanged: variants " + new Gson().toJson(variants));
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
                    Log.i(TAG, "onChanged: tax " + new Gson().toJson(tax));
                    StringBuilder mStringBuilder = new StringBuilder();
                    mStringBuilder.append(tax.getName() + " = " + tax.getValue()+"%");
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
