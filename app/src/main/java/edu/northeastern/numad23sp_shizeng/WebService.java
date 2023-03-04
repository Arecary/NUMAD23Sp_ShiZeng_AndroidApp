package edu.northeastern.numad23sp_shizeng;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WebService extends AppCompatActivity implements View.OnClickListener {
    private String sp1;
    private String sp2;
    private String show;
    private Spinner spinner1;
    private Spinner spinner2;
    private TextView showRate;
    private ImageView dollar1;
    private ImageView dollar2;
    private String rate;
    Boolean flag;
    ProgressBar progressBar;
    Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                if (rate.equals("null")) {
                    showRate.setText("fail to find exchange rate, please try again");
                    dollar1.setImageResource(R.drawable.sad48);
                    dollar2.setImageResource(R.drawable.sad48);
                } else {
                    showRate.setText(show);
                }
            } else if (msg.what == 1) {
                progressBar.setVisibility(progressBar.VISIBLE);
            } else if (msg.what == 2) {
                progressBar.setVisibility(progressBar.GONE);
            } else if (msg.what == 3) {
                if (flag) {
                    switch (sp1) {
                        case "USD":
                            dollar1.setImageResource(R.drawable.usd100);
                            break;
                        case "CAD":
                            dollar1.setImageResource(R.drawable.cad48);
                            break;
                        case "CNY":
                            dollar1.setImageResource(R.drawable.cny64);
                            break;
                        case "AUD":
                            dollar1.setImageResource(R.drawable.aud48);
                            break;
                        case "EUR":
                            dollar1.setImageResource(R.drawable.eur48);
                            break;
                    }
                }

            } else if (msg.what == 4) {
                if (flag) {
                    switch (sp2) {
                        case "USD":
                            dollar2.setImageResource(R.drawable.usd100);
                            break;
                        case "CAD":
                            dollar2.setImageResource(R.drawable.cad48);
                            break;
                        case "CNY":
                            dollar2.setImageResource(R.drawable.cny64);
                            break;
                        case "AUD":
                            dollar2.setImageResource(R.drawable.aud48);
                            break;
                        case "EUR":
                            dollar2.setImageResource(R.drawable.eur48);
                            break;
                    }
                }

            }
        }
    };



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_service);
        showRate = findViewById(R.id.tv_ExchangeRate);
        dollar1 = findViewById(R.id.iv_dollar1);
        dollar2 = findViewById(R.id.iv_dollar2);
        progressBar = findViewById(R.id.proBar_Exchange);
        progressBar.setVisibility(progressBar.GONE);
        Button button = findViewById(R.id.btn_MoneyTransform);
        button.setOnClickListener(this);
        spinner1 = findViewById(R.id.sp_Exchange1);
        spinner2 = findViewById(R.id.sp_Exchange2);

    }


    @Override
    public void onClick(View view) {
        MyThread t = new MyThread();
        t.start();
    }


    class MyThread extends Thread {

        @Override
        public void run() {
            sp1 = spinner1.getSelectedItem().toString();
            sp2 = spinner2.getSelectedItem().toString();
            EditText editText = (EditText) findViewById(R.id.et_Money);
            String amount = editText.getText().toString();
            String path = "https://api.exchangerate.host/convert?from=" + sp1
                    + "&to=" + sp2 + "&amount=" + amount;
            try {
                Message m = new Message();
                Message m2 = new Message();
                Message m3 = new Message();
                Message m4 = new Message();
                Message m5= new Message();
                Message m6 = new Message();
                m3.what = 1;
                m3.obj = 1;
                handler.sendMessage(m3);
                Thread.sleep(2000);
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setRequestMethod("GET");
                int repCode = conn.getResponseCode();
                if (repCode != 200) {
                    show = "fail to find exchange rate, please try again";
                }
                InputStream inStream = conn.getInputStream();
                String resp = getResponse(inStream);
                JSONObject jObject = new JSONObject(resp);
                String amount2 = jObject.getString("result");
                JSONObject  rateInfo = jObject.getJSONObject("info");
                rate = rateInfo.getString("rate");
                if (rate.equals("null")) {
                    flag = false;
                } else {
                    flag = true;
                }
                String date = jObject.getString("date");
                show = "On " + date + ": " + "\n" + sp1 + " to " + sp2 + " exchange rate is " + "1 : "
                + rate + "\n" + amount + " " + sp1 + " can exchange " + amount2 + " " + sp2;
                m.what = 0;
                m4.what = 2;
                m.obj = show;
                m2.obj = rate;
                m4.obj = 1;
                m5.what = 3;
                m6.what = 4;
                m5.obj = sp1;
                m6.obj = sp2;
                handler.sendMessage(m);
                handler.sendMessage(m2);
                handler.sendMessage(m4);
                handler.sendMessage(m5);
                handler.sendMessage(m6);
                conn.disconnect();
            } catch (IOException | JSONException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public String getResponse(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String len;
            while ((len = bufferedReader.readLine()) != null) {
                stringBuilder.append(len);
            }
            bufferedReader.close();
            return stringBuilder.toString().replace(",", ",\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}