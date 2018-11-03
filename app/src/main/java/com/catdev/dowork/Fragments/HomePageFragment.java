package com.catdev.dowork.Fragments;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.QuickContactBadge;
import android.widget.Toast;

import com.catdev.dowork.Activities.HomePageActivity;
import com.catdev.dowork.R;
import com.catdev.dowork.Utilities.TaskListItemHolder;
import com.catdev.dowork.Utilities.taskListAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomePageFragment extends Fragment {
    RecyclerView taskListView;
    ArrayList<TaskListItemHolder> itemList;
    ArrayList<String> itemListId;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_page, container, false);

        taskListView = view.findViewById(R.id.tasksListView);

        taskListView.setHasFixedSize(true);
        taskListView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        itemListId = new ArrayList<>();

        updateTasks();

        return view;
    }

    private void updateTasks () {
        final ArrayList<String> titles = new ArrayList<>();
        final ArrayList<String> descriptions = new ArrayList<>();
        itemList = new ArrayList<>();
        ParseQuery<ParseObject> taskListQuery = new ParseQuery<>("Tasks");
        taskListQuery.whereEqualTo("reservationBy", "");
        taskListQuery.whereEqualTo("isCompleted", false);
        taskListQuery.addDescendingOrder("createdAt");

        taskListQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if(e == null) {
                    for (ParseObject object : objects) {
                        String title = object.getString("title");
                        String desc = object.getString("desc");
                        String id = object.getObjectId();

                        Log.i("tasklistitem", "title: " + title + " ; desc: " + desc);

                        titles.add(title);
                        descriptions.add(desc);
                        itemListId.add(id);
                    }
                    displayTasks(titles, descriptions);
                } else {
                    Toast.makeText(getContext(), "Błąd wczytywania zleceń", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void displayTasks(ArrayList<String> titles, ArrayList<String> descriptions) {
        for(int i = 0; i < titles.size() ; i++) {
            itemList.add(new TaskListItemHolder(this.getContext(), titles.get(i), descriptions.get(i)));
        }

        RecyclerView.Adapter adapter = new taskListAdapter(itemList, this.getContext());
        taskListView.setAdapter(adapter);
    }
}
