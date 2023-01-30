package edu.northeastern.numad23sp_shizeng;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Clicky extends AppCompatActivity implements View.OnClickListener {

    private TextView press;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicky);
        press = findViewById(R.id.text_Pressed);
        Button btn_A = findViewById(R.id.button_A);
        btn_A.setOnClickListener(this);
        Button btn_B = findViewById(R.id.button_B);
        btn_B.setOnClickListener(this);
        Button btn_C = findViewById(R.id.button_C);
        btn_C.setOnClickListener(this);
        Button btn_D = findViewById(R.id.button_D);
        btn_D.setOnClickListener(this);
        Button btn_E = findViewById(R.id.button_E);
        btn_E.setOnClickListener(this);
        Button btn_F = findViewById(R.id.button_F);
        btn_F.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int theId = view.getId();
        if (theId == R.id.button_A) {
            String result = String.format("Press: %s", "A");
            press.setText(result);
        } else if (theId == R.id.button_B) {
            String result = String.format("Press: %s", "B");
            press.setText(result);
        } else if (theId == R.id.button_C) {
            String result = String.format("Press: %s", "C");
            press.setText(result);
        } else if (theId == R.id.button_D) {
            String result = String.format("Press: %s", "D");
            press.setText(result);
        } else if (theId == R.id.button_E) {
            String result = String.format("Press: %s", "E");
            press.setText(result);
        } else if (theId == R.id.button_F) {
            String result = String.format("Press: %s", "F");
            press.setText(result);
        }

    }
}