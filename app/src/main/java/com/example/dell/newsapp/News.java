package com.example.dell.newsapp;

public class News {

    private String nHeadline;

    private String nDate;

    private String nBrief;

    private String nUrl;

    public News (String Headline, String Date, String Brief, String url){

        nHeadline = Headline;
        nDate = Date;
        nBrief = Brief;
        nUrl = url;
    }

    public String getHeadline(){
        return nHeadline;
    }

    public String getDate(){
        return nDate;
    }

    public String getBrief(){
        return nBrief;
    }

    public String getUrl(){
        return nUrl;
    }
}
