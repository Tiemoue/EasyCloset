package com.example.easycloset;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.parse.ParseException;
import com.parse.ParseFile;
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
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 20;
    public static String TAG = ".CameraActivity";
    File photoFile;

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
                if(e == null){
                    goToMainActivity();
                }
            }
        });

    }


    private void launchCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference for future access
        String photoFileName = "photo.jpg";
        photoFile = getPhotoFileUri(photoFileName);
        // wrap File object into a content provider
        Uri fileProvider = FileProvider.getUriForFile(this, "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);
        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(this.getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                ibProfileImg.setImageBitmap(takenImage);
            } else { // Result was a failure
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private File getPhotoFileUri(String photoFileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            mediaStorageDir.mkdirs();
        }
        // Return the file target for the photo based on filename
        return new File(mediaStorageDir.getPath() + File.separator + photoFileName);
    }

}