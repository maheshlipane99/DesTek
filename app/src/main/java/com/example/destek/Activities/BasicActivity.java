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


public class BasicActivity extends AppCompatActivity {
    public RecyclerView recyclerView;
    public EditText editSearch;
    private String TAG = BasicActivity.class.getSimpleName();
    public ProgressBar progressBar;
    private TextView textMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initialize();

    }

    public void setActionBarName(String tiltle) {
        getSupportActionBar().setTitle(tiltle);
    }

    public void initialize() {
        recyclerView = findViewById(R.id.recyclerView);
        editSearch = findViewById(R.id.editSearch);
        textMessage = findViewById(R.id.textMessage);
        progressBar = findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

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
