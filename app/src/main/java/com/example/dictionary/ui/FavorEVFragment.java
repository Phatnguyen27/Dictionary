package com.example.dictionary.ui;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import com.example.dictionary.DatabaseAccess;
import com.example.dictionary.DatabaseOpenHelper;
import com.example.dictionary.MyListAdapter;
import com.example.dictionary.MyWord;
import com.example.dictionary.R;

import java.util.ArrayList;

public class FavorEVFragment extends Fragment {
    private String title;
    private int page;
    private DatabaseAccess dbAccess;
    private RecyclerView recyclerView;
    private View root;
    private AutoCompleteTextView textView;
    private static ArrayList<MyWord> listOfWord;
    private static MyListAdapter adapter;

    // newInstance constructor for creating fragment with arguments
    public static FavorEVFragment newInstance(int page, String title) {
        FavorEVFragment fragmentFirst = new FavorEVFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_favor_ev, container, false);
        dbAccess = DatabaseAccess.getInstance(getActivity(), DatabaseOpenHelper.DB_NAME_EV);
        recyclerView = root.findViewById(R.id.r_view);
        textView = root.findViewById(R.id.search_view);
        return root;
    }
}
