package edu.northeastern.numad23sp_shizeng.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import edu.northeastern.numad23sp_shizeng.R;

public class RegisterPage extends AppCompatActivity implements View.OnClickListener {

    private EditText etName, etAge, etEmail, etPassword;
    private Button btnRegister;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        btnRegister = (Button) findViewById(R.id.btn_RegisterPage_Register);
        btnRegister.setOnClickListener(this);

        etName = (EditText) findViewById(R.id.et_RegisterName);
        etAge = (EditText) findViewById(R.id.et_RegisterAge);
        etEmail = (EditText) findViewById(R.id.et_RegisterEmail);
        etPassword = (EditText) findViewById(R.id.et_RegisterPassword);
        mAuth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.proBar_Register);
    }

    @Override
    public void onClick(View view) {

        Intent i = new Intent();
        if (view.getId() == R.id.btn_RegisterPage_Register) {
            register();
        }
    }

    private void register() {
        String name = etName.getText().toString().trim();
        String age = etAge.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (name.isEmpty()) {
            etName.setError("Name is required!");
            etName.requestFocus();
            return;
        }

        if (age.isEmpty()) {
            etAge.setError("age is required!");
            etAge.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            etEmail.setError("email is required!");
            etEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Please provide valid email!");
            etEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            etPassword.setError("password is required!");
            etPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(name, age, email);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(RegisterPage.this, "Register successfully!", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                                startActivity(new Intent(RegisterPage.this, FireBase.class));
                                            } else {
                                                Toast.makeText(RegisterPage.this, "Register Failed1, please try again!", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(RegisterPage.this, "Register Failed, this address has already registered or password invalid, please try again!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });



    }
}