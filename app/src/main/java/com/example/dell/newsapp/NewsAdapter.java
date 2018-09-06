package com.example.dell.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {

    /*Variables to be used when formatting time*/
    private static final String T_SEPERATOR = "T";
    private static String Z_SEPERATOR = "Z";

    public NewsAdapter(Context context, List<News> news) {
        super(context, 0, news);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list_item, parent, false);
        }

        News currentNews = getItem(position);

        TextView sectionTextView = (TextView) listItemView.findViewById(R.id.section);
        sectionTextView.setText(currentNews.getHeadline());

        /*Split the date and time format, to look nice and seperated, and to also remove unwanted characters in the
         * final value*/
        String originalDate = currentNews.getDate();
        String splitDate = "";
        String splitTime = "";

        if (originalDate.contains(T_SEPERATOR)) {
            String[] part = originalDate.split(T_SEPERATOR);
            splitDate = part[0] + "\n";
            splitTime = part[1];
        }

        String finalDate = splitDate + splitTime;

        if (finalDate.contains(Z_SEPERATOR)) {
            String[] parts = finalDate.split(Z_SEPERATOR);
            finalDate = parts[0];
        }

        /*Set the text views to display the data they are supposed to, aided by the adapter*/

        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date);
        dateTextView.setText(finalDate);

        TextView briefTextView = (TextView) listItemView.findViewById(R.id.brief);
        briefTextView.setText(currentNews.getBrief());

        TextView authorTextView = (TextView) listItemView.findViewById(R.id.author);
        authorTextView.setText(currentNews.getAuthor());

        return listItemView;
    }
}
