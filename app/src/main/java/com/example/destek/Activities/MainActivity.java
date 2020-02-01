package com.example.destek.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.destek.DB.tabels.Category;
import com.example.destek.DB.viewModel.CategoryViewModel;
import com.example.destek.R;
import com.example.destek.Utils.Const;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.progressBar)
    public ProgressBar progressBar;

    @BindView(R.id.textMessage)
    public TextView textMessage;

    @BindView(R.id.layoutButtons)
    public LinearLayout layoutButtons;

    @BindView(R.id.btnShopByCat)
    Button btnShopByCat;

    @BindView(R.id.btnTopViews)
    Button btnTopViews;

    @BindView(R.id.btnTopOrder)
    Button btnTopOrder;

    @BindView(R.id.btnTopShare)
    Button btnTopShare;

    CategoryViewModel categoryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        btnShopByCat.setOnClickListener(this);
        btnTopViews.setOnClickListener(this);
        btnTopOrder.setOnClickListener(this);
        btnTopShare.setOnClickListener(this);

        showProgressBar();
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        categoryViewModel.getAllItem().observe(MainActivity.this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> mCategoryList) {
                if (mCategoryList != null && mCategoryList.size() > 0) {
                    showDataView();
                } else {
                    showNoData();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnShopByCat: {
                startActivity(new Intent(this, CategoriesListActivity.class));
                break;
            }

            case R.id.btnTopViews: {
                startActivity(new Intent(this, RankProductListActivity.class)
                        .putExtra(Const.EXTRA_RANK_ID, 1)
                        .putExtra(Const.EXTRA_RANK_TITLE, "Most Viewed Products"));
                break;
            }
            case R.id.btnTopOrder: {
                startActivity(new Intent(this, RankProductListActivity.class)
                        .putExtra(Const.EXTRA_RANK_ID, 2)
                        .putExtra(Const.EXTRA_RANK_TITLE, "Most Ordered Products"));
                break;
            }
            case R.id.btnTopShare: {
                startActivity(new Intent(this, RankProductListActivity.class)
                        .putExtra(Const.EXTRA_RANK_ID, 3)
                        .putExtra(Const.EXTRA_RANK_TITLE, "Most Shared Products"));
                break;
            }
        }
    }

    public void showDataView() {
        progressBar.setVisibility(View.GONE);
        textMessage.setVisibility(View.GONE);
        layoutButtons.setVisibility(View.VISIBLE);
    }

    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        layoutButtons.setVisibility(View.GONE);
    }

    public void showNoData() {
        layoutButtons.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        textMessage.setVisibility(View.VISIBLE);
        textMessage.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_sad_emoji, 0, 0);
        textMessage.setText("Sorry..! No data found");
    }

}
