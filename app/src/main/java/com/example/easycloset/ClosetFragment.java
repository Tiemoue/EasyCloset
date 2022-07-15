package com.example.easycloset;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class ClosetFragment extends Fragment {

    private ItemsAdapter adapter;
    private List<Item> allItems;
    private MainActivity activity;
    private ProgressDialog progressDialog;

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
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        RecyclerView rvItems = view.findViewById(R.id.rvItems);
        rvItems.setLayoutManager(gridLayoutManager);
        allItems = new ArrayList<>();
        adapter = new ItemsAdapter(getContext(), allItems);
        rvItems.setAdapter(adapter);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle(getString(R.string.updating_closet));
        progressDialog.show();
        queryPosts();
        Button addBtn = view.findViewById(R.id.btAddClothes);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.setFragmentContainer(activity.getUploadFragment());
            }
        });
    }

    public ItemsAdapter getAdapter() {
        return adapter;
    }

    public List<Item> getAllItems() {
        return allItems;
    }

    @SuppressLint("NotifyDataSetChanged")
    protected void queryPosts() {
        ParseQuery<Item> query = ParseQuery.getQuery(Item.class);
        query.addDescendingOrder("createdAt");
        query.findInBackground((items, e) -> {
            if (e != null) {
                progressDialog.dismiss();
                activity.closeApp();
            }
            progressDialog.dismiss();
            // save received posts to list and notify adapter of new data
            allItems.clear();
            allItems.addAll(items);
            adapter.notifyDataSetChanged();
        });
    }
}