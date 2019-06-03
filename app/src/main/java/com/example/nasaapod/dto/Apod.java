package com.example.nasaapod.dto;

import java.util.Objects;

public class Apod {

    private String date;

    private String explanation;

    private String hdurl;

    private String media_type;

    private String service_version;

    private String title;

    private String url;

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return this.date;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getExplanation() {
        return this.explanation;
    }

    public void setHdurl(String hdurl) {
        this.hdurl = hdurl;
    }

    public String getHdurl() {
        return this.hdurl;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public String getMedia_type() {
        return this.media_type;
    }

    public void setService_version(String service_version) {
        this.service_version = service_version;
    }

    public String getService_version() {
        return this.service_version;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Apod apod = (Apod) o;
        return date.equals(apod.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }
}
