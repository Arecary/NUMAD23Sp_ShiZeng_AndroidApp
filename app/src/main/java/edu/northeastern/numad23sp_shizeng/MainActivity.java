package edu.northeastern.numad23sp_shizeng;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.northeastern.numad23sp_shizeng.firebase.FireBase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1 = (Button) findViewById(R.id.button_AboutMe);
        Button button2 = (Button) findViewById(R.id.button_Clicky);
        Button button3 = (Button) findViewById(R.id.button_LinkCollector);
        Button button4 = (Button) findViewById(R.id.button_Prime);
        Button button5 = findViewById(R.id.btn_WebService);
        Button button6 = findViewById(R.id.btn_FireBase);
        Button button7 = findViewById(R.id.btn_location);
/*        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setClass(MainActivity.this, AboutMe.class);
                startActivity(i);
                *//*Toast.makeText(MainActivity.this, "Shi Zeng\n" + "zeng.shi1@northeastern.edu", Toast.LENGTH_LONG).show();*//*
                Log.i("Click", "onClick: AboutMe");
            }
        });*/

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent();
        if (view.getId() == R.id.button_AboutMe) {
            i.setClass(this, AboutMe.class);
            startActivity(i);
        } else if (view.getId() == R.id.button_Clicky) {
            i.setClass(this, Clicky.class);
            startActivity(i);
        } else if (view.getId() == R.id.button_LinkCollector) {
            i.setClass(this, LinkCollector.class);
            startActivity(i);
        } else if (view.getId() == R.id.button_Prime) {
            i.setClass(this, PrimeDirective.class);
            startActivity(i);
        } else if (view.getId() == R.id.btn_WebService) {
            i.setClass(this, WebService.class);
            startActivity(i);
        } else if (view.getId() == R.id.btn_FireBase) {
            i.setClass(this, FireBase.class);
            startActivity(i);
        } else if (view.getId() == R.id.btn_location) {
            i.setClass(this, Location.class);
            startActivity(i);
        }
    }

}