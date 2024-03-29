package com.example.dictionary.ui;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AutoCompleteTextView;

import com.example.dictionary.DatabaseAccess;
import com.example.dictionary.DatabaseOpenHelper;
import com.example.dictionary.MyListAdapter;
import com.example.dictionary.MyWord;
import com.example.dictionary.R;
import com.example.dictionary.SearchAdapter;

import java.util.ArrayList;


public class FavorVEFragment extends Fragment {

    private RecyclerView rvItems;
    private static ArrayList<MyWord> listOfWord;
    private static MyListAdapter adapter;
    private LinearLayoutManager mLayoutManager;

    private AutoCompleteTextView mAcTvSearch;
    private ArrayList<String> searchList;
    private SearchAdapter searchAdapter;
    private int currentIndex, totalItems, scrollOutItems, offset;
    private boolean isScrolling;
    private View root;
    private DatabaseAccess dbAccess;
    // newInstance constructor for creating fragment with arguments
    public FavorVEFragment() {

    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_favor_ev, container, false);
        currentIndex = 260;
        offset = 10;
        dbAccess = DatabaseAccess.getInstance(getActivity(), DatabaseOpenHelper.DB_NAME_VE);
        addControl();
        addEvent();
//        if (listOfWord.isEmpty()) {
//            dbAccess = DatabaseAccess.getInstance(getActivity(), DatabaseOpenHelper.DB_NAME_EV);
//            dbAccess.open();
//            listOfWord = dbAccess.getWords();
//            dbAccess.close();
//        }

//        searchView = (SearchView) root.findViewById(R.id.search_view);
//        String data[]={"Emmanuel", "Olayemi", "Henrry", "Mark", "Steve", "Ayomide", "David", "Anthony", "Adekola", "Adenuga"};
//        SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_view);
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.search_item, data);
//        searchAutoComplete.setAdapter(dataAdapter);
//        searchAdapter = new SearchAdapter(getContext(),R.layout.search_item,searchList);
//        searchView.setSuggestionsAdapter(searchAdapter);
//        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                // collapse the view ?
//                //menu.findItem(R.id.menu_search).collapseActionView();
//                Log.e("queryText",query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                // search goes here !!
//                // listAdapter.getFilter().filter(query);
//                Log.e("queryText",newText);
//                return false;
//            }
//        });
        return root;
    }

    private void addEvent() {
        fetchMoreData();
    }

    private void addControl() {
        rvItems = (RecyclerView) root.findViewById(R.id.r_view);
        listOfWord = new ArrayList<MyWord>();
        searchList = new ArrayList<String>();
        adapter = new MyListAdapter(listOfWord);
        mLayoutManager = new LinearLayoutManager(getActivity());
        rvItems.setLayoutManager(mLayoutManager);
        rvItems.setAdapter(adapter);
        rvItems.setHasFixedSize(true);
        rvItems.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentIndex = mLayoutManager.getChildCount();
                totalItems = mLayoutManager.getItemCount();
                scrollOutItems = mLayoutManager.findFirstVisibleItemPosition();
                if (isScrolling && (currentIndex + scrollOutItems == totalItems)) {
                    isScrolling = false;
                    new FavorVEFragment.WordLoaderTask().execute();
                }
            }
        });
        mAcTvSearch = root.findViewById(R.id.search_view);
        searchAdapter = new SearchAdapter(getContext(),R.layout.search_item,searchList);
        mAcTvSearch.setAdapter(searchAdapter);
        mAcTvSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                searchList.clear();
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                new FavorVEFragment.WordSearcher().execute();
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    class WordLoaderTask extends AsyncTask<Void, Void, Void> {

        protected Void doInBackground(Void... params) {
            fetchMoreData();
            return null;
        }

        protected void onPostExecute(Void param) {
            adapter.notifyDataSetChanged();
        }
    }

    class WordSearcher extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... params) {
            searchString();
            return null;
        }

        protected void onPostExecute(Void param) {
            searchAdapter.notifyDataSetChanged();
        }
    }

    public void fetchMoreData() {
        dbAccess.open();
        ArrayList<MyWord> temp = dbAccess.getFavouriteWord(totalItems + 20, offset);
        for (int i = 0; i < temp.size(); i++) {
            listOfWord.add(new MyWord(temp.get(i)));
        }
        dbAccess.close();
    }

    public void searchString() {
        String str = mAcTvSearch.getText().toString();
        dbAccess.open();
        ArrayList<String> temp = dbAccess.getWordsStartWith(str);
        for (int i = 0; i < temp.size(); i++) {
            searchList.add(temp.get(i));
        }
        dbAccess.close();
    }
}
