package com.example.easycloset;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.parse.Parse;
import com.parse.ParseFile;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {
    private Context context;
    private List<Item> items;

    public ItemsAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Item> list) {
        items.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsAdapter.ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvType;
        private ImageView ivClothesImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvType = itemView.findViewById(R.id.tvType);
            ivClothesImage = itemView.findViewById(R.id.ivClotheImage);
        }

        public void bind(Item item) {
            tvType.setText(item.getColour());
            ParseFile image = item.getImage();
            Glide.with(context).load(image.getUrl()).transform(new RoundedCorners(90)).into(ivClothesImage);
        }
    }
}