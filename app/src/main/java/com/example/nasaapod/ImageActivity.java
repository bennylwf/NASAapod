package com.example.nasaapod;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nasaapod.dto.ApodDate;
import com.example.nasaapod.viewmodel.ApodViewModel;


public class ImageActivity extends AppCompatActivity {

    private LinearLayoutManager mLayoutManager;
    private RecyclerView recyclerView;
    private RvAdapter mAdapter;
    private ApodViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


        viewModel = ViewModelProviders.of(this).get(ApodViewModel.class);
        recyclerView = findViewById(R.id.recyclerView);

        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RvAdapter(viewModel.getEmpltyApods(getApodDate()));
        recyclerView.setAdapter(mAdapter);

        bindViewModelToView();
    }

    public void bindViewModelToView() {
        viewModel.toLiveData(viewModel.getApodObservable( this.getApodDate()))
                .observe(this, mAdapter::updateApodImage);
    }

    private ApodDate getApodDate() {
        Bundle bundle = getIntent().getExtras();
        return (ApodDate) bundle.get("apodDate");
    }

    @Override
    protected void onDestroy() {
        recyclerView.setAdapter(null);
        super.onDestroy();
    }
}
