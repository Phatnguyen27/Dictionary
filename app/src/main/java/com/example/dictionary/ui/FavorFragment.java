package com.example.dictionary.ui;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.dictionary.Fav_PageAdapter;
import com.example.dictionary.R;
import com.google.android.material.tabs.TabLayout;


public class FavorFragment extends Fragment{
    FragmentStatePagerAdapter adapter;
    TabLayout tabsStrip;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favor, container, false);
        final ViewPager viewPager = (ViewPager) root.findViewById(R.id.pager);
        tabsStrip = root.findViewById(R.id.tablayout);
        adapter = new Fav_PageAdapter(getFragmentManager());
        viewPager.setCurrentItem(0);


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(adapter);
        tabsStrip.setupWithViewPager(viewPager);
        return root;
    }
}