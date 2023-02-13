package edu.northeastern.numad23sp_shizeng;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class PrimeDirective extends AppCompatActivity implements View.OnClickListener {

    private int number = 3;
    private TextView checkboxShow;
    private Boolean flag = true; // to check the user click prime process or Terminate_Search.
    private Boolean flag2 = true; // make sure only one thread of prime is running.
    private Boolean flagSave = false; // to check if have savedInstanceState.
    private Boolean flagCheckBox = false; // to check the status of checkbox.
    private String show1;
    private String show2;
    private String showFinal = "latest prime found: 3" + "\n" +
            "Current number being checked: null";
    ;
    Handler handler = new Handler();

    @SuppressLint({"HandlerLeak", "CutPasteId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            flagSave = savedInstanceState.getBoolean("flagSave");
            flagCheckBox = savedInstanceState.getBoolean("flagCheckBox");
        }
        if (flagSave) {
            number = savedInstanceState.getInt("number");
            flag = savedInstanceState.getBoolean("flag");
            flag2 = savedInstanceState.getBoolean("flag2");
            show1 = savedInstanceState.getString("show1");
            show2 = savedInstanceState.getString("show2");
        }
        setContentView(R.layout.activity_prime_directive);
        checkboxShow = findViewById(R.id.checkbox_number);
        checkboxShow.setText(showFinal);
        CheckBox checkBox = findViewById(R.id.checkbox_number);
        if (flagCheckBox) {
            checkBox.setChecked(true);
        }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                flagCheckBox = isChecked;
            }
        });
        Button b1 = (Button) findViewById(R.id.btn_Find_Primes);
        Button b2 = (Button) findViewById(R.id.btn_Terminate_Search);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        if (flagSave && flag) {
            MyThread myThread = new MyThread();
            myThread.start();
        }
    }

    @Override
    public void onBackPressed() {
        if (!flag2) {
            View v = findViewById(R.id.checkbox_number);
            Snackbar.make(v, "Terminate the search?", Snackbar.LENGTH_INDEFINITE).setAction("Confirm", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            }).show();
        } else {
            finish();
        }
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_Find_Primes) {
            if (flag2) {
                flag = true;
                number = 3;
                showFinal = "latest prime found: 3" + "\n" +
                        "Current number being checked: null";
                ;
                checkboxShow.setText(showFinal);
                MyThread myThread = new MyThread();
                myThread.start();
            }
        } else if (view.getId() == R.id.btn_Terminate_Search) {
            flag = false;
            flag2 = true;
            flagSave = false;
            showFinal = "latest prime found: " + show1 + "\n" +
                    "Current number being checked: null";
            checkboxShow.setText(showFinal);
            show1 = "3";
            show2 = "null";
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        if (flag && number != 3) {
            savedInstanceState.putInt("number", number);
            savedInstanceState.putBoolean("flag", flag);
            savedInstanceState.putBoolean("flag2", flag2);
            savedInstanceState.putString("show1", show1);
            savedInstanceState.putString("show2", show2);
            flagSave = true;
            savedInstanceState.putBoolean("flagSave", flagSave);
        }
        savedInstanceState.putBoolean("flagCheckBox", flagCheckBox);
        super.onSaveInstanceState(savedInstanceState);
    }

    class MyThread extends Thread {
        @Override
        public void run() {
            while (flag) {
                flag2 = false;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        show1 = String.valueOf(number);
                        show2 = String.valueOf(number + 2);
                        showFinal = "latest prime found: " + show1 + "\n" +
                                "Current number being checked: " + show2;
                        checkboxShow.setText(showFinal);
                        number += 2;
                    }
                });
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}