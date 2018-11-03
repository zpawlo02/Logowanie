package com.catdev.dowork.Utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.catdev.dowork.R;

public class TaskListItemHolder {
    private Bitmap image;
    private String title;
    private String shortenedDesc;
    Context context;

    public TaskListItemHolder(Context context, String title, String shortenedDesc) {
        this.context = context;
        this.image = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_lock_black_24dp);
        this.title = title;
        if(shortenedDesc.length() > 100) {
            this.shortenedDesc = shortenedDesc.substring(0, 99) + "...";
        } else
            this.shortenedDesc = shortenedDesc;
    }
    public TaskListItemHolder(Context context, Bitmap image, String title, String shortenedDesc) {
        this(context, title, shortenedDesc);
        this.image = image;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getShortenedDesc() {
        return shortenedDesc;
    }
}
