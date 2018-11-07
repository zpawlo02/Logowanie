package com.catdev.dowork.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.catdev.dowork.Activities.HomePageActivity;
import com.catdev.dowork.R;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class ProfilePageFragment extends Fragment {
    TextView nameTextView;
    TextView ratingTextView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_page, container, false);

        nameTextView = view.findViewById(R.id.nameTextView);
        ratingTextView = view.findViewById(R.id.ratingTextView);

        ParseQuery<ParseUser> userQurey = ParseUser.getQuery();
        userQurey.whereEqualTo("objectId", HomePageActivity.getProfileIdToShow());
        userQurey.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(e == null) {
                    ParseObject object = objects.get(0);
                    nameTextView.setText(object.getString("username"));
                }
            }
        });

        return view;
    }
}
