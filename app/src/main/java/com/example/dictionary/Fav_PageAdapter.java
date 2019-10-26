package com.example.dictionary;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.dictionary.ui.FavorEVFragment;
import com.example.dictionary.ui.FavorVEFragment;

public class Fav_PageAdapter extends FragmentStatePagerAdapter  {
    private String tabTitles[] = new String[] { "English - Vietnamese", "Vietnamese - English"};

    public Fav_PageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new FavorEVFragment();
            case 1: return new FavorVEFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "English - Vietnamese";
            case 1:
                return "Vietnam - English";
                default: return null;
        }
    }
}
