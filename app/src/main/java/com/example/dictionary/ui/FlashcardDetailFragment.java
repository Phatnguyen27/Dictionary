package com.example.dictionary.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.dictionary.R;

public class FlashcardDetailFragment extends Fragment {
    private String title;
    private int page;
    private TextView tv_word,tv_pronunciation,tv_score,tv_result;
    private ImageButton record_button;

    // newInstance constructor for creating fragment with arguments
    public static FlashcardDetailFragment newInstance(int page, String title) {
        FlashcardDetailFragment fragmentFirst = new FlashcardDetailFragment();
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
        View view = inflater.inflate(R.layout.fragment_flashcard_detail, container, false);
        tv_word = view.findViewById(R.id.Word);
        tv_pronunciation = view.findViewById(R.id.pronunciation);
        tv_result = view.findViewById(R.id.result);
        tv_score = view.findViewById(R.id.score);
        record_button = view.findViewById(R.id.record_button);
        return view;
    }
}
