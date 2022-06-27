package com.example.easycloset;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddItemActivity extends AppCompatActivity {

    private Button btnCoats;
    private Button btnJackets;
    private Button btnSweaters;
    private Button btnShirts;
    private Button btnHoodies;
    private Button btnShorts;
    private Button btnPants;
    private Button btnJoggers;
    private Button btnBoots;
    private Button btnSneakers;
    private Button btnSlides;
    private Button btnHeadwear;
    private Button btnDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);

        btnDone = findViewById(R.id.btDone);
        btnCoats = findViewById(R.id.btCoat);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainActivity();
            }
        });

        btnCoats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              goToCameraActivity();
            }
        });
    }

    private void goToMainActivity() {
        Intent intent = new Intent(AddItemActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void goToCameraActivity(){
        Intent intent = new Intent(AddItemActivity.this, CameraActivity.class);
        startActivity(intent);
    }


}