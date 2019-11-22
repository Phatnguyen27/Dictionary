package com.example.dictionary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class FlashcardPageAdapter extends PagerAdapter {
    private Context mContext;
    private int mLayout;
    private ArrayList<MyWord> mList;
    public FlashcardPageAdapter(Context context,int layout,ArrayList<MyWord> list) {
        mContext = context;
        mLayout = layout;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
    @Override
    public Object instantiateItem(ViewGroup container,int position) {
        LayoutInflater inflayter = LayoutInflater.from(mContext);
        View layout = inflayter.inflate(mLayout,container,false);
        TextView tvWord = layout.findViewById(R.id.Word);
        TextView tvSub = layout.findViewById(R.id.Word_Sub);
        MyWord word = mList.get(position);
        tvWord.setText(word.getWord());
        tvSub.setText("Meaning");
        container.addView(layout);
        return layout;
    }
    @Override
    public void destroyItem(ViewGroup container,int position,Object object) {
        container.removeView((View)object);
    }
}
