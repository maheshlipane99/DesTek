package com.example.destek.Activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.destek.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;


public class BasicActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    public RecyclerView recyclerView;

    @BindView(R.id.editSearch)
    public EditText editSearch;
    private String TAG = BasicActivity.class.getSimpleName();

    @BindView(R.id.progressBar)
    public ProgressBar progressBar;

    @BindView(R.id.textMessage)
    public TextView textMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        ButterKnife.bind(this);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }

    public void setActionBarName(String tiltle) {
        getSupportActionBar().setTitle(tiltle);
    }

    public void showRecyclerView() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        editSearch.setVisibility(View.VISIBLE);
    }

    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        editSearch.setVisibility(View.GONE);
    }


    public void showNoData() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        editSearch.setVisibility(View.GONE);
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



}
