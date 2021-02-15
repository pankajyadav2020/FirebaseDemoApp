package com.example.firebasedemoapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    EditText name,email,pass;
    private static final int AUTHUI_REQUEST_CODE = 0;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name=findViewById(R.id.editTextTextPersonName);
        email=findViewById(R.id.editTextTextEmailAddress);
        pass=findViewById(R.id.editTextTextPassword);

    }

    public void loginHandler(View view) {
        // Validation
        if (name.getText().toString().isEmpty()){
            name.setError("name cannot be empty");
        }
        else if (email.getText().toString().isEmpty()){
            email.setError("email cannot be empty");
        }
        else if (pass.getText().toString().isEmpty()){
            pass.setError("password cannot be empty");
        }
        else {
            startActivityForResult(AuthUI.getInstance()
                    .createSignInIntentBuilder().setAvailableProviders(Arrays.asList(
                            new AuthUI.IdpConfig.PhoneBuilder().build()
                    )).build(), AUTHUI_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTHUI_REQUEST_CODE) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Intent intent=new Intent(this,HomeActivity.class);
                startActivity(intent);
                // ...
            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
    }
}