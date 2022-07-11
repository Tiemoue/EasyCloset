package com.example.easycloset;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.SignUpCallback;

import java.io.File;

public class SignUpActivity extends AppCompatActivity {

    private String genderChoice;
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
        etUsername = findViewById(R.id.etSignupUsername);
        etPassword = findViewById(R.id.etSignupPassword);
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

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etFirstName.getText().toString().isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Invalid Username", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (etLastName.getText().toString().isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Invalid 2", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (etPassword.getText().toString().isEmpty() || etUsername.getText().toString().isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Invalid 3", Toast.LENGTH_SHORT).show();
                    return;
                }
                createAccount(etFirstName, etLastName, etPassword, etUsername, genderChoice);
            }
        });
    }

    private void goToMainActivity() {
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void createAccount(EditText firstname, EditText lastname, EditText password, EditText username, String gender) {
        User user = new User();
        user.setUsername(String.valueOf(username.getText()));
        user.setPassword(String.valueOf(password.getText()));
        user.setKeyFirstName(String.valueOf(firstname.getText()));
        user.setKeyLastName(String.valueOf(lastname.getText()));
        user.setKeyGender(gender);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    goToMainActivity();
                }
            }
        });

    }

}