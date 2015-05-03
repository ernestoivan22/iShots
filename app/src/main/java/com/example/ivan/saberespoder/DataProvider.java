package com.example.ivan.saberespoder;

/**
 * Created by Ivan on 28/04/2015.
 */
public class DataProvider {
    //holi
    private String title;
    private String content;
    private float rating;

    public DataProvider(String title, String content, float rating){
        this.title = title;
        this.content = content;
        this.rating = rating;
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
