package com.example.wangyiyun.fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.wangyiyun.R;
import com.example.wangyiyun.adapter.SearchSongs;
import com.example.wangyiyun.adapter.SongsAdapter;
import com.example.wangyiyun.litepal.Information;

import org.litepal.crud.DataSupport;

import java.util.List;



public class Show extends AppCompatActivity implements View.OnClickListener {

    private List<Information> songsList;
    private Button back;
    private TextView clear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        if (Build.VERSION.SDK_INT >= 21) {
            View decroView = getWindow().getDecorView();
            decroView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);//布局显示在状态栏上面
            getWindow().setStatusBarColor(Color.TRANSPARENT);//设置颜色为透明色
        }
        try{
            InitReccler();
        }catch (Exception e){
            e.printStackTrace();
        }

        clear = findViewById(R.id.show_clear);
        back = findViewById(R.id.show_back);
        clear.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.show_back:
                finish();
                break;
            case R.id.show_clear:
                DataSupport.deleteAll(Information.class);
                InitReccler();
                break;
            default:
                break;
        }
    }
    public void InitReccler(){
        songsList = DataSupport.findAll(Information.class);
        RecyclerView recyclerView = findViewById(R.id.show_recently);
        Adapter adapter = new Adapter(songsList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }
}
