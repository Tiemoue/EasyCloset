package com.example.easycloset;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.parse.ParseFile;

import java.io.File;

public class UploadFragment extends Fragment {

    private EditText etColour;
    private ImageView ivPicture;
    private File photoFile;
    private MainActivity activity;
    private String category;

    public UploadFragment() {
    }

    public UploadFragment(MainActivity mainActivity) {
        this.activity = mainActivity;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upload, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button btnTakePicture = view.findViewById(R.id.btnTakePicture);
        Button btnUpload = view.findViewById(R.id.btnUpload);
        etColour = view.findViewById(R.id.etColour);
        ivPicture = view.findViewById(R.id.ivPicture);
        Spinner spCategory = view.findViewById(R.id.spCategory);
        Button btnCloset = view.findViewById(R.id.btCloset);
        Camera camera = new Camera(getContext(), photoFile);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.category, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spCategory.setAdapter(adapter);

        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnTakePicture.setOnClickListener(v -> camera.launchCamera());

        btnCloset.setOnClickListener(v -> activity.setFragmentContainer(activity.getClosetFragment()));

        btnUpload.setOnClickListener(v -> {
            String colour = etColour.getText().toString();
            if (colour.isEmpty()) {
                Toast.makeText(getContext(), "Colour cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            if (photoFile != null && ivPicture.getDrawable() != null) {
                saveItem(colour, category, photoFile);
            } else {
                Toast.makeText(getContext(), "no image found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public File getPhotoFile() {
        return photoFile;
    }

    public ImageView getIvPicture() {
        return ivPicture;
    }


    @SuppressLint("NotifyDataSetChanged")
    private void saveItem(String colour, String category, File photoFile) {
        Item item = new Item();
        item.setColour(colour);
        item.setCategory(category);
        item.setImage(new ParseFile(photoFile));
        item.saveInBackground(e -> {
            if (e != null) {
                Toast.makeText(getContext(), "Error while saving", Toast.LENGTH_SHORT).show();
            } else {
                etColour.setText("");
                ivPicture.setImageResource(0);
                Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
                activity.getClosetFragment().getAllItems().add(0, item);
                activity.getClosetFragment().getAdapter().notifyDataSetChanged();
                activity.getClosetFragment().queryPosts();
                activity.setFragmentContainer(activity.getClosetFragment());
            }
        });
    }
}