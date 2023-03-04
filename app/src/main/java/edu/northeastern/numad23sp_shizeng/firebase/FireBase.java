package edu.northeastern.numad23sp_shizeng.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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

import edu.northeastern.numad23sp_shizeng.AboutMe;
import edu.northeastern.numad23sp_shizeng.R;

public class FireBase extends AppCompatActivity implements View.OnClickListener{

    private EditText etEmail, etPassword;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_base);

        mAuth = FirebaseAuth.getInstance();

        Button button1 = findViewById(R.id.btn_Login);
        Button button2 = findViewById(R.id.btn_ResetPassword);
        Button button3 = findViewById(R.id.btn_Register);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);

        etEmail = (EditText) findViewById(R.id.et_Email);
        etPassword = (EditText) findViewById(R.id.et_Password);
        progressBar = (ProgressBar) findViewById(R.id.proBar_Login);
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent();
        if (view.getId() == R.id.btn_Register) {
            i.setClass(this, RegisterPage.class);
            startActivity(i);
        } else if (view.getId() == R.id.btn_Login) {
            userLogin();
        }
    }

    private void userLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty()) {
            etEmail.setError("Email is required!");
            etEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            etPassword.setError("Password is required!");
            etPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    /*// 第一次登录验证注册邮箱
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user.isEmailVerified()) {
                        startActivity(new Intent(FireBase.this, UserHomePage.class));
                        progressBar.setVisibility(View.GONE);
                    }else {
                        user.sendEmailVerification();
                        progressBar.setVisibility(View.GONE);
                    }*/
                    startActivity(new Intent(FireBase.this, UserHomePage.class));
                    progressBar.setVisibility(View.GONE);

                }else {
                    Toast.makeText(FireBase.this, "Failed to login!", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}