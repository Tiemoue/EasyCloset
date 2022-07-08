package com.example.easycloset;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.io.File;

public class SignUpActivity extends AppCompatActivity {

    private String genderChoice;
    private ImageButton ibProfileImg;
    private EditText etUsername;
    private EditText etPassword;
    private EditText etFirstName;
    private EditText etLastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Spinner spinner = findViewById(R.id.spGenders);
        Button btnSignUp = findViewById(R.id.btSignUpAccount);
        ibProfileImg = findViewById(R.id.ibProfilePic);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etFirstName = findViewById(R.id.etFirstname);
        etLastName = findViewById(R.id.etSignUpLastname);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.genders, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                genderChoice = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnSignUp.setOnClickListener(v -> goToMainActivity());
    }

    private void goToMainActivity() {
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void createAccount(EditText firstname, EditText lastname, EditText password, EditText username, String gender, File image){
        User user = new User();
// Set core properties
        user.setUsername(String.valueOf(username.getText()));
        user.setPassword(String.valueOf(password.getText()));
        
// Set custom properties

        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

            }
        });
// Invoke signUpInBackground

    }
}