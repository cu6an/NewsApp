package com.example.dell.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    private String qUrl;

    public NewsLoader(Context context, String Url) {
        super(context);
        qUrl = Url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {

        if (qUrl == null) {
            return null;
        }

        /*Perform the network request, parse the response, and extract a list of news*/
        List<News> news = QueryNews.fetchNewsData(qUrl);
        return news;
    }
}
