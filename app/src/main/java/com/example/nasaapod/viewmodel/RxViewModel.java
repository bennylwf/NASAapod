package com.example.nasaapod.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.Observable;

public class RxViewModel extends ViewModel {


    public <T> LiveData<T> toLiveData(Observable<T> observable) {
        Observable<T> observableWithoutError = observable
                .onErrorResumeNext(e -> { return Observable.empty(); });
        return new RxLiveData<>(observableWithoutError);
    }
}
