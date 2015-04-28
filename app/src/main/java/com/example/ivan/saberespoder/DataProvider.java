package com.example.ivan.saberespoder;

/**
 * Created by Ivan on 28/04/2015.
 */
public class DataProvider {
    //holi
    private String title;
    private String content;
    private String rating;

    public DataProvider(String title, String content, String rating){
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
