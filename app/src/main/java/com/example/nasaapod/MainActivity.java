package com.example.nasaapod;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.nasaapod.databinding.ActivityMainBinding;
import com.example.nasaapod.dto.ApodDate;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        ApodDate apodDate = new ApodDate();
        apodDate.setStartDate("2019-03-01");
        apodDate.setEndDate("2019-03-15");
        binding.setApodDate(apodDate);

        binding.button.setOnClickListener(this::loadNasa);
    }

    private void loadNasa(View view) {

//        binding.getApodDate().setStartDate("2019-03-01");
        String startDate = binding.getApodDate().getStartDate();
        Log.d(TAG, "Start Date: " + startDate);
        String endDate = binding.getApodDate().getEndDate();
        Log.d(TAG, "End Date: " + endDate);

        Intent intent = new Intent(this, ImageActivity.class);
        intent.putExtra("apodDate", binding.getApodDate());
        this.startActivity(intent);
    }
}
