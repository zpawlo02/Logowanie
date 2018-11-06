package com.catdev.dowork.Utilities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.catdev.dowork.R;

import java.util.ArrayList;

public class taskListAdapter extends RecyclerView.Adapter<taskListAdapter.ViewHolder> {

    ArrayList<TaskListItemHolder> taskItemList;
    Context context;

    public taskListAdapter(ArrayList<TaskListItemHolder> taskItemList, Context context) {
        this.taskItemList = taskItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.task_item_list, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        TaskListItemHolder item = taskItemList.get(i);
        viewHolder.title.setText(item.getTitle());
        viewHolder.shortenedDesc.setText(item.getShortenedDesc());
    }

    @Override
    public int getItemCount() {
        return taskItemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView shortenedDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTextView);
            shortenedDesc = itemView.findViewById(R.id.descTextView);
        }
    }
}
