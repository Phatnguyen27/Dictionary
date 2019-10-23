package com.example.dictionary;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
    private ArrayList<MyWord> ListData;
    public MyListAdapter(ArrayList<MyWord> ListData) {
        this.ListData = ListData;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MyWord myListData = ListData.get(position);
        holder.textView.setText(myListData.getWord());
        String content = myListData.getContent();
        Pattern pattern = Pattern.compile("<ul><li>(.*?)<");
        Matcher matcher = pattern.matcher(content);
        String mini = "";
        if (matcher.find())
        {
            mini = matcher.group(1);
        }
        holder.miniContent.setText(mini);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), WordActivity.class);
                intent.putExtra("word", myListData);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ListData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public TextView miniContent;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.textView);
            this.miniContent = (TextView) itemView.findViewById(R.id.mini_content);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayout);
        }
    }
}