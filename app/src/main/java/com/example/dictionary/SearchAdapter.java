package com.example.dictionary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;
import java.util.List;


public class SearchAdapter extends ArrayAdapter<String> {
    private Context context;
    private int resource;
    private List<String> arrWords, tempContacts, suggestions;

    public SearchAdapter(Context context, int resource, ArrayList<String> arrWords) {
        super(context, resource, arrWords);
        this.context = context;
        this.resource = resource;
        this.arrWords = arrWords;
        this.tempContacts = new ArrayList<String>(arrWords);
        this.suggestions = new ArrayList<String>(arrWords);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.search_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mTvSearch = (TextView) convertView.findViewById(R.id.tv_search);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String word = arrWords.get(position);
        viewHolder.mTvSearch.setText(word);
        return convertView;
    }

    public class ViewHolder {
        TextView mTvSearch;
    }
    @Override
    public Filter getFilter(){
        return myFilter;
    }
    Filter myFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (String word : tempContacts) {
                    if (word.toLowerCase()
                            .startsWith(constraint.toString().toLowerCase())) {
                        suggestions.add(word);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            ArrayList<String> c =  (ArrayList<String> )results.values ;
            if (results != null && results.count > 0) {
                clear();
                for (String str : c) {
                    add(str);
                    notifyDataSetChanged();
                }
            }
            else{
                clear();
                notifyDataSetChanged();
            }
        }
    };
}
