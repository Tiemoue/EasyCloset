package com.example.easycloset;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class CameraActivity extends AppCompatActivity {

    private Button btnTakePicture;
    private EditText etColour;
    private Button btnUpload;
    private ImageView ivPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        btnTakePicture = findViewById(R.id.btnTakePicture);
        btnUpload = findViewById(R.id.btnUpload);
    }
}