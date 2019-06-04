package com.example.nasaapod.viewmodel;

import com.example.nasaapod.dto.Apod;
import com.example.nasaapod.dto.ApodDate;
import com.example.nasaapod.io.NasaService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class ApodViewModel extends RxViewModel{

    public Observable<Apod> getApodObservable(ApodDate apodDate) {
        return Observable.fromIterable(getDates(apodDate))
                .subscribeOn(Schedulers.io())
                .flatMap(NasaService.getInstance()::getApod)
                .doOnNext(apod -> {System.out.println("Got apod for date: " + apod.getDate());})
                .filter(apod -> apod.getUrl().startsWith("https://apod.nasa.gov/"));
    }

    public List<Apod> getEmpltyApods(ApodDate apodDate) {
        return Observable.fromIterable(getDates(apodDate)).map(s -> {
            Apod apod = new Apod();
            apod.setDate(s);
            return apod;
        }).toList().blockingGet();
    }


    public List<Apod> getApods( ApodDate apodDate) {

        return this.getApodObservable(apodDate)
                .toSortedList((a1, a2) -> a1.getDate().compareTo(a2.getDate()))
                .blockingGet();


    }



    public List<String> getDates( ApodDate apodDate) {

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
