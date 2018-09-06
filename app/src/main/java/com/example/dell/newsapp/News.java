package com.example.dell.newsapp;

public class News {

    /*This is the custom news class to be used, here, we declare all the variables to be used */
    private String nHeadline;

    private String nDate;

    private String nBrief;

    private String nUrl;

    private String nAuthor;

    /*This is the public constructor to be used, and also contains the necessary arguments*/
    public News(String Headline, String Date, String Brief, String url, String author) {

        nHeadline = Headline;
        nDate = Date;
        nBrief = Brief;
        nUrl = url;
        nAuthor = author;
    }

    /*Here, we come up with getter methods to help retreive data of the different variables*/
    public String getHeadline() {
        return nHeadline;
    }

    public String getDate() {
        return nDate;
    }

    public String getBrief() {
        return nBrief;
    }

    public String getUrl() {
        return nUrl;
    }

    public String getAuthor() {
        return nAuthor;
    }
}
