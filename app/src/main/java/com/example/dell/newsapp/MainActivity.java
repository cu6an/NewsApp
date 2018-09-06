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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.content.*;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    private static final int NEWS_LOADER_ID = 1;


    /*The URL for the guardian api that we use to get the json information that we want
     * this particular url is edited to include tags to help get the author*/
    private static final String GUARDIAN_QUERY_URL
            = "https://content.guardianapis.com/search?&show-tags=contributor&api-key=735a6888-2220-4341-9c61-e14a3ff1cf87";

    private TextView emptyState;

    private NewsAdapter nAdapter;

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        return new NewsLoader(this, GUARDIAN_QUERY_URL);
    }

    /*When loading is finished and there are no news stories to show, we set the text view to show
     * the corresponding message, and then clear resources by clearing the adapter*/
    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        emptyState.setText(R.string.no_news);

        nAdapter.clear();

        if (news != null && !news.isEmpty()) {
            nAdapter.addAll(news);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        nAdapter.clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Here, we initialize the listview, and set it to an xml layout, and after we attach an adapter
         * to it that will help populate the list view with data*/
        ListView newsListView = (ListView) findViewById(R.id.list);
        nAdapter = new NewsAdapter(this, new ArrayList<News>());
        newsListView.setAdapter(nAdapter);

        /*We trigger an intent to open the users's browser, everytime a news listitem is clicked on
         * We accomplis this by setting an onClickListener to every listview item*/
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

    @Override
    // This method initialize the contents of the Activity's options menu.
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the Options Menu we specified in XML
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
