package edu.northeastern.numad23sp_shizeng;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class LinkCollector extends AppCompatActivity implements View.OnClickListener {
    private int count = 0;
    private String url;
    private UrlAdapter adapter;
    List<Url> urlList;
    RecyclerView urlRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_collector);
        Log.d("Shi_Test", "The activity state---->onCreate");
        urlList = new ArrayList<>();

        urlRecyclerView = findViewById(R.id.rv_Url);
        /*urlRecyclerView.setBackgroundColor();*/
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