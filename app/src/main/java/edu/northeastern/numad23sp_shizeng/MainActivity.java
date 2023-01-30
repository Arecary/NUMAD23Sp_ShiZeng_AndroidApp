package edu.northeastern.numad23sp_shizeng;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1 = (Button)findViewById(R.id.button_AboutMe);
        Button button2 = (Button)findViewById(R.id.button_Clicky);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Shi Zeng\n"+"zeng.shi1@northeastern.edu", Toast.LENGTH_LONG).show();
                Log.i("Click", "onClick: AboutMe");
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setClass(MainActivity.this, Clicky.class);
                startActivity(i);
                Log.i("Click", "onClick: AboutMe");
            }
        });
    }
}