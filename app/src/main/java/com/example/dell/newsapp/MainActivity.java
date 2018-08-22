package com.example.dell.newsapp;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.content.Intent;
import android.net.Uri;
import android.content.Loader;
import android.app.LoaderManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.content.*;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>>{

    private static final int NEWS_LOADER_ID = 1;

    private static final String GUARDIAN_QUERY_URL
            = "https://content.guardianapis.com/search?api-key=735a6888-2220-4341-9c61-e14a3ff1cf87";

    private TextView emptyState;

    private NewsAdapter nAdapter;

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        return new NewsLoader(this,GUARDIAN_QUERY_URL);
    }

    @Override
    public void onLoadFinished(android.content.Loader<List<News>> loader, List<News> news) {
        emptyState.setText(R.string.no_news);

        nAdapter.clear();

        if (news != null && !news.isEmpty()) {
            nAdapter.addAll(news);
        }
    }

    @Override
    public void onLoaderReset(android.content.Loader<List<News>> loader) {
        nAdapter.clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView newsListView = (ListView) findViewById(R.id.list);
        nAdapter = new NewsAdapter(this, new ArrayList<News>());
        newsListView.setAdapter(nAdapter);

    newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            News currentNews = nAdapter.getItem(i);
            Uri NewsUri = Uri.parse(currentNews.getUrl());

            Intent webIntent = new Intent(Intent.ACTION_VIEW, NewsUri);

            startActivity(webIntent);
        }
    });

        emptyState = (TextView) findViewById(R.id.empty_view);
        newsListView.setEmptyView(emptyState);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data

        if (networkInfo != null && networkInfo.isConnected()) {

            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            // Update empty state with no connection error message

            emptyState.setText(R.string.no_internet_connection);

        }

    }
}
