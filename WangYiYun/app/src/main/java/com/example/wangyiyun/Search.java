package com.example.wangyiyun;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.wangyiyun.adapter.SearchSongs;
import com.example.wangyiyun.adapter.SongsAdapter;
import com.example.wangyiyun.util.HttpUrl;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Search extends AppCompatActivity implements View.OnClickListener {

    private List<SearchSongs> songsList = new ArrayList<>();
    private EditText text;
    private Button back;
    private  RecyclerView recyclerView1;
    private Button enter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serach);

        main.activityList.add(this);
        if(Build.VERSION.SDK_INT >= 21) {
            View decroView = getWindow().getDecorView();
            decroView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            |View.SYSTEM_UI_FLAG_LAYOUT_STABLE);//布局显示在状态栏上面
            getWindow().setStatusBarColor(Color.TRANSPARENT);//设置颜色为透明色
        }

       recyclerView1 =  findViewById(R.id.view_recycler);
        text= findViewById(R.id.search_sing);
        enter = findViewById(R.id.enter);
        enter.setOnClickListener(this);
        back = findViewById(R.id.back);
        back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.enter:
                String name = text.getText().toString().trim();
                InitSongs(name);
                break;
                default:
                    break;
        }
    }

    private void InitSongs(String name){
        try {
            String path = "https://c.y.qq.com/soso/fcgi-bin/client_search_cp?p=1&n=50&w=" + name + "&format=json";
            HttpUrl.sendHttpResult(path, new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    String data = response.body().string();
                    parseJSONWithJSONObject(data);
                }

            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void parseJSONWithJSONObject(String jsonData){
        try{

            songsList.clear();
            JSONObject objectAll = new JSONObject(jsonData);
            String data = objectAll.optString("data",null);
            if(!TextUtils.isEmpty(data)){
                JSONObject object = new JSONObject(data);
                String song = object.optString("song",null);
                JSONObject objectSong = new JSONObject(song);
                String list = objectSong.optString("list",null);
                JSONArray array = new JSONArray(list);
                for(int i = 0; i < array.length(); i ++){
                    JSONObject trueObject = array.getJSONObject(i);
                    String singer = trueObject.optString("singer",null);
                    String alumnId = trueObject.optString("albummid",null);
                    JSONArray jsonArray = new JSONArray(singer);
                    String singerName = null;
                    for(int j = 0; j < jsonArray.length(); j ++){
                        JSONObject oobject = jsonArray.getJSONObject(j);
                        singerName = oobject.optString("name",null);
                    }
                    String songName = trueObject.optString("songname","null");
                    String songmid = trueObject.optString("songmid",null);
                    SearchSongs songs = new SearchSongs(singerName,songName,songmid,alumnId);
                    songsList.add(songs);
                }

            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LinearLayoutManager layoutManager = new LinearLayoutManager(Search.this);
                    recyclerView1.setLayoutManager(layoutManager);
                    SongsAdapter adapter = new SongsAdapter(songsList);
                    recyclerView1.setAdapter(adapter);
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
