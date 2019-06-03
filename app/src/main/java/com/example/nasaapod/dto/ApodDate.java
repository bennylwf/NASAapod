package com.example.nasaapod.dto;

import android.os.Parcel;
import android.os.Parcelable;

public class ApodDate implements Parcelable {

    private String startDate;
    private String endDate;

    protected ApodDate(Parcel in) {
        startDate = in.readString();
        endDate = in.readString();
    }

    public ApodDate() {

    }

    public static final Creator<ApodDate> CREATOR = new Creator<ApodDate>() {
        @Override
        public ApodDate createFromParcel(Parcel in) {
            return new ApodDate(in);
        }

        @Override
        public ApodDate[] newArray(int size) {
            return new ApodDate[size];
        }
    };

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(startDate);
        dest.writeString(endDate);
    }
}
