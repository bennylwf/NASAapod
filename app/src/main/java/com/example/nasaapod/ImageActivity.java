package com.example.nasaapod;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nasaapod.dto.Apod;
import com.example.nasaapod.dto.ApodDate;
import com.example.nasaapod.io.NasaService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class ImageActivity extends AppCompatActivity {

    private LinearLayoutManager mLayoutManager;
    private RecyclerView recyclerView;
    private RvAdapter mAdapter;

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


        recyclerView = findViewById(R.id.recyclerView);

        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RvAdapter(getEmpltyApods());
        recyclerView.setAdapter(mAdapter);



        updateImages();
    }

    private void updateImages() {
        List<String> dates = getDates();
        getApodObservable(dates).observeOn(AndroidSchedulers.mainThread())
                .filter(apod -> apod.getUrl().startsWith("https://apod.nasa.gov/")).subscribe(mAdapter::updateApod);
    }

    private List<Apod> getEmpltyApods() {
        return Observable.fromIterable(getDates()).map(s -> {
            Apod apod = new Apod();
            apod.setDate(s);
            return apod;
        }).toList().blockingGet();
    }


    protected List<Apod> getApods() {
        List<String> dates = getDates();

        return getApodObservable(dates)
                .toSortedList((a1, a2) -> a1.getDate().compareTo(a2.getDate()))
                .blockingGet();


    }

    private Observable<Apod> getApodObservable(List<String> dates) {
        return Observable.fromIterable(dates)
                .subscribeOn(Schedulers.io())
                .flatMap(NasaService.getInstance()::getApod);
    }

    private List<String> getDates() {
        Bundle bundle = getIntent().getExtras();
        ApodDate apodDate = (ApodDate) bundle.get("apodDate");
        LocalDate startDate = LocalDate.parse(apodDate.getStartDate());
        LocalDate endDate = LocalDate.parse(apodDate.getEndDate());


        List<String> dates = new ArrayList();
        while (startDate.isBefore(endDate) || startDate.equals(endDate)) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            dates.add(startDate.format(formatter));
            startDate = startDate.plusDays(1);
        }
        return dates;
    }
}
