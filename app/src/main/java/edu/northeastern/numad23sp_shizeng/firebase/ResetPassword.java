package edu.northeastern.numad23sp_shizeng.firebase;

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

    private EditText email;
    private Button resetPasswordButton;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        email = (EditText) findViewById(R.id.et_ResetPasswordEmail);
        resetPasswordButton = (Button) findViewById(R.id.btn_Reset);
        progressBar = (ProgressBar) findViewById(R.id.proBar_Reset);

        mAuth = FirebaseAuth.getInstance();

        resetPasswordButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        reSetPassword();
    }

    private void reSetPassword() {
        String emailInput = email.getText().toString().trim();

        if (emailInput.isEmpty()) {
            email.setError("Email is required!");
            email.requestFocus();
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