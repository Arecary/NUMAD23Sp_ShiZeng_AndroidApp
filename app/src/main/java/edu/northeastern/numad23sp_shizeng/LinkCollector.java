package edu.northeastern.numad23sp_shizeng;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class LinkCollector extends AppCompatActivity implements View.OnClickListener {
    private int count = 0;
    Boolean flag = false;
    private String url;
    private UrlAdapter adapter;
    List<Url> urlList;
    RecyclerView urlRecyclerView;


/*    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        if (!flag) {
            flag = true;
            savedInstanceState.putBoolean("flag", flag);
            int length = adapter.getItemCount();
            System.out.println("length is " + length);
            for (int i = 0; i < length; i++) {
                savedInstanceState.putString("myUrl"+i, adapter.getData().get(i).getUrl());
                System.out.println("-------------------------------------------");
                System.out.println("myUrl"+i);
                System.out.println(adapter.getData().get(i).getUrl());
                System.out.println("-------------------------------------------");
            }
            super.onSaveInstanceState(savedInstanceState);
        }


    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        flag = savedInstanceState.getBoolean("flag");
        if (flag) {
            super.onRestoreInstanceState(savedInstanceState);
            List<Url> urlList2 = new ArrayList<>();
//            Intent intent =getIntent();
            int length = savedInstanceState.size();
            System.out.println("urlList length is " + length);
            for (int i = 0; i < length; i++) {
                String s = savedInstanceState.getString("myUrl"+i);
//                Url u = (Url)intent.getSerializableExtra("myUrl"+i);
                if (s!=null) {
                    Url u = new Url(s);
                    urlList2.add(u);
                }
            }
            System.out.println("urlList size is " + urlList2.size());
            urlRecyclerView = findViewById(R.id.rv_Url);
            urlRecyclerView.setHasFixedSize(true);
            urlRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new UrlAdapter(urlList2, this);
            urlRecyclerView.setAdapter(adapter);
            int size = urlList2.size();
            for (int i = 0; i < size; i++) {
                Url u = urlList2.get(i);
                adapter.addData(i,u);
            }
            for (int i = 0; i < size-1; i++) {
                adapter.removeData(i);
            }
            adapter.removeData(0);


        }
        else {
            flag = true;
        }

    }*/
    /*@Override
    protected void onResume() {

        super.onResume();
        System.out.println("9999999999999");
    }*/


/*    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        switch (newConfig.orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                urlRecyclerView = findViewById(R.id.rv_Url);
                urlRecyclerView.setHasFixedSize(true);
                urlRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                urlRecyclerView = findViewById(R.id.rv_Url);
                urlRecyclerView.setHasFixedSize(true);
                urlRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                break;
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            /*urlList = new ArrayList<>();*/
            flag = savedInstanceState.getBoolean("flag");
            /*Intent intent =getIntent();
            int length = savedInstanceState.size();
            for (int i = 0; i < length-1; i++) {
                Url u = (Url)intent.getSerializableExtra("myUrl"+i);
                urlList.add(u);
            }
            System.out.println("urlList size is " + urlList.size());
            urlRecyclerView = findViewById(R.id.rv_Url);
            urlRecyclerView.setHasFixedSize(true);
            urlRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            adapter = new UrlAdapter(urlList, this);
            System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzz");
            urlRecyclerView.setAdapter(adapter);
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxx");*/
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_collector);
        Log.d("Shi_Test", "The activity state---->onCreate");
        if (!flag) {
            urlList = new ArrayList<>();
            urlRecyclerView = findViewById(R.id.rv_Url);
            urlRecyclerView.setHasFixedSize(true);
            urlRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new UrlAdapter(urlList, this);
            urlRecyclerView.setAdapter(adapter);
            adapter.setRecyclerItemClickListener(new UrlAdapter.OnRecyclerItemClickListener() {
                @Override
                public void OnRecyclerItemClick(int pos) {
                    url = "https://www." + urlList.get(pos).getUrl();
                    Intent intent = new Intent();
                /*Bundle bundle = new Bundle();
                bundle.putString("link", url);
                intent.putExtras(bundle);*/
                    intent.setAction("android.intent.action.VIEW");
                    Uri u = Uri.parse(url);
                    intent.setData(u);
                    startActivity(intent);
                }
            });

            Button button1 = (Button) findViewById(R.id.btn_AddUrl);
            button1.setOnClickListener(this);

        }
        else {
            flag = false;
        }

    }

    @Override
    public void onClick(View view) {

        String s = "";
        EditText editText = (EditText) findViewById(R.id.et_InputUrl);
        s = editText.getText().toString();
        Url url = new Url(s);
        adapter.addData(count, url);
//        urlList.add(url);
        count++;
        /*adapter.notifyItemInserted(1);
        adapter.notifyItemRangeChanged(1,urlList.size()-1);*/
        if (!s.equals("")) {
            View v = findViewById(R.id.rv_Url);
            Snackbar.make(v, "Create success", Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adapter.removeData(count - 1);
                    count--;
                }
            }).show();
        } else {
            Toast toast = Toast.makeText(LinkCollector.this, "Url can not be empty", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

        editText.setText("");
    }


}