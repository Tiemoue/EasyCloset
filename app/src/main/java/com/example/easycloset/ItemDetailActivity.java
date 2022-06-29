package com.example.easycloset;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import java.util.Date;

public class ItemDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        ImageView ivItem = findViewById(R.id.ivItem);
        TextView tvCategory = findViewById(R.id.tvItemCategory);
        TextView tvColour = findViewById(R.id.tvItemColor);
        TextView tvDate = findViewById(R.id.tvDateAdded);
        ImageButton btnDelete = findViewById(R.id.ibDelete);

        Item item = getIntent().getParcelableExtra(Item.class.getSimpleName());

        Date createdAt = item.getCreatedAt();
        String time = Item.calculateTimeAgo(createdAt);

        tvCategory.setText(item.getCategory());
        tvColour.setText(item.getColour());
        tvDate.setText(time);
        ParseFile image = item.getImage();
        Glide.with(this).load(image.getUrl()).into(ivItem);

        btnDelete.setOnClickListener(v -> {
            item.deleteInBackground();
            finish();
        });
    }
}