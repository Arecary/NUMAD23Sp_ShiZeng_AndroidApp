package edu.northeastern.numad23sp_shizeng.firebase;

import android.util.Patterns;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import edu.northeastern.numad23sp_shizeng.R;

public class ResetPassword extends AppCompatActivity implements View.OnClickListener {

    private EditText etEmail;
    private Button resetPasswordButton, backLogin;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        etEmail = (EditText) findViewById(R.id.et_ResetPasswordEmail);
        resetPasswordButton = (Button) findViewById(R.id.btn_Reset);
        backLogin = (Button) findViewById(R.id.btn_BackLogin);
        progressBar = (ProgressBar) findViewById(R.id.proBar_Reset);

        mAuth = FirebaseAuth.getInstance();

        resetPasswordButton.setOnClickListener(this);
        backLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_Reset) {
            reSetPassword();
        } else if (view.getId() == R.id.btn_BackLogin) {
            finish();
        }

    }

    private void reSetPassword() {
        String emailInput = etEmail.getText().toString().trim();

        if (emailInput.isEmpty()) {
            etEmail.setError("Email is required!");
            etEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            etEmail.setError("Please provide valid email!");
            etEmail.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.sendPasswordResetEmail(emailInput).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ResetPassword.this, "Check your email to reset password!", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                } else {
                    Toast.makeText(ResetPassword.this, "Sth wrong!", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}