package com.example.easycloset;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.parse.DeleteCallback;
import com.parse.ParseException;
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
        String time = Item.Time(createdAt);

        tvCategory.setText(item.getCategory());
        tvColour.setText(item.getColour());
        tvDate.setText(time);
        ParseFile image = item.getImage();
        Glide.with(this).load(image.getUrl()).into(ivItem);

        btnDelete.setOnClickListener(v -> {
            item.deleteInBackground(new DeleteCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Intent intent = new Intent(ItemDetailActivity.this, MainActivity.class);
                        intent.putExtra(Item.class.getSimpleName(), item);
                        setResult(RESULT_OK, intent);
                        finish();
                    } else {
                        Toast.makeText(ItemDetailActivity.this, "error while deleting item", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
    }
}