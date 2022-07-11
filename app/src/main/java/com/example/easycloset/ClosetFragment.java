package com.example.easycloset;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ClosetFragment extends Fragment {

    private ItemsAdapter adapter;
    private List<Item> allItems;
    private MainActivity activity;
    private ProgressDialog progressDialog;

    public ClosetFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        allItems.clear();
        queryPosts();
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
        FloatingActionButton btnAddClothes = view.findViewById(R.id.floatingAction);

        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        RecyclerView rvItems = view.findViewById(R.id.rvItems);
        rvItems.setLayoutManager(gridLayoutManager);
        allItems = new ArrayList<>();
        adapter = new ItemsAdapter(getContext(), allItems);
        rvItems.setAdapter(adapter);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Updating Closet...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        btnAddClothes.setOnClickListener(v -> activity.setFragmentContainer(activity.getUploadFragment()));
    }

    public ItemsAdapter getAdapter() {
        return adapter;
    }

    public List<Item> getAllItems() {
        return allItems;
    }

    @SuppressLint("NotifyDataSetChanged")
    protected void queryPosts() {
        // specify what type of data we want to query - Post.class
        ParseQuery<Item> query = ParseQuery.getQuery(Item.class);
        // include data referred by user key
        query.include(Item.KEY_USER);
        query.whereEqualTo(Item.KEY_USER, ParseUser.getCurrentUser());
        // order posts by creation date (newest first)
        query.addDescendingOrder("createdAt");
        // start an asynchronous call for posts
        query.findInBackground((items, e) -> {
            // check for errors
            if (e != null) {
                return;
            }
            // save received posts to list and notify adapter of new data
            progressDialog.dismiss();
            allItems.addAll(items);
            adapter.notifyDataSetChanged();
        });
    }
}