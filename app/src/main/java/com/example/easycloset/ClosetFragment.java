package com.example.easycloset;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class ClosetFragment extends Fragment {

    private ItemsAdapter adapter;
    private List<Item> allItems;
    private RecyclerView rvItems;
    private Button btnAddClothes;
    private MainActivity activity;

    public ClosetFragment() {
    }

    public ClosetFragment(MainActivity mainActivity) {
        activity = mainActivity;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_closet, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        btnAddClothes = view.findViewById(R.id.btAddClothes);

        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        rvItems = view.findViewById(R.id.rvItems);
        rvItems.setLayoutManager(gridLayoutManager);
        allItems = new ArrayList<>();
        adapter = new ItemsAdapter(getContext(), allItems);
        rvItems.setAdapter(adapter);

        queryPosts(0);

        btnAddClothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.fragmentManager.beginTransaction().replace(R.id.flContainer, activity.getAddFragment()).commit();
            }
        });
    }

    public ItemsAdapter getAdapter() {
        return adapter;
    }

    public List<Item> getAllItems() {
        return allItems;
    }

    public RecyclerView getRvItems() {
        return rvItems;
    }

    protected void queryPosts(int skip) {
        // specify what type of data we want to query - Post.class
        ParseQuery<Item> query = ParseQuery.getQuery(Item.class);
        // include data referred by user key
        query.include(Item.KEY_USER);
        // limit query to latest 20 items
        query.setLimit(20);
        query.setSkip(skip);
        // order posts by creation date (newest first)
        query.addDescendingOrder("createdAt");
        // start an asynchronous call for posts
        query.findInBackground(new FindCallback<Item>() {
            @Override
            public void done(List<Item> items, ParseException e) {
                // check for errors
                if (e != null) {
                    return;
                }
                // save received posts to list and notify adapter of new data
                allItems.addAll(items);
                adapter.notifyDataSetChanged();
            }
        });
    }
}