package com.example.dictionary.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.dictionary.DatabaseAccess;
import com.example.dictionary.FlashcardPageAdapter;
import com.example.dictionary.MyWord;
import com.example.dictionary.R;

import java.util.ArrayList;

public class FlashcardFragment extends Fragment {
    private ViewPager viewPager;
    private FlashcardPageAdapter adapter;
    ArrayList<MyWord> mList;
    int mCurrentPage = 0;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_flashcard, container, false);
        viewPager = root.findViewById(R.id.pager);
        mList = DatabaseAccess.getRandomList(getActivity());
        adapter = new FlashcardPageAdapter(getActivity(),R.layout.fragment_flashcard_detail,mList);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(mCurrentPage);
        return root;
    }
}