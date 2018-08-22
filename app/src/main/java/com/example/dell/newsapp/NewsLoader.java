package com.example.dell.newsapp;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    private String qUrl;

    public NewsLoader(Context context, String Url){
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

        List<News> news = QueryNews.fetchNewsData(qUrl);
        return news;
    }
}
