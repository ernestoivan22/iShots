package com.example.ivan.saberespoder;

/**
 * Created by Ivan on 28/04/2015.
 */
public class DataProvider {

    private String title;
    private String content;
    private float rating;
    private String contentExtra;

    public DataProvider(String title, String content, float rating, String contentExtra){
        this.title = title;
        this.content = content;
        this.rating = rating;
        this.contentExtra = contentExtra;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public String getContentExtra() {
        return contentExtra;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
