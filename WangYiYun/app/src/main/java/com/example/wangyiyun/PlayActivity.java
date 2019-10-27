package com.example.wangyiyun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.wangyiyun.util.GetHttpUrl;
import com.example.wangyiyun.util.HttpUrl;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;


import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PlayActivity extends AppCompatActivity {

    private String singerName;
    MediaPlayer mediaPlayer;
    private TextView songsName;
    private TextView singer;
    private Button start;
    private CircleImageView image;
    private Button back;
    private String result;
    private TextView show;
    private String playResult;
    private SeekBar seekBar;
    private static final int UPDATE_TEXT = 1;
    private static final int UPDATE = 0;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case UPDATE_TEXT:
                    playResult = String.valueOf(msg.obj);
                    if(playResult != null){
                        InitMedia(playResult);
                    }
                   break;
                case UPDATE:
                    try {
                        seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    handler.sendEmptyMessageDelayed(UPDATE,500);
                    break;
                    default:
                        break;
            }


        }
    };
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        main.activityList.add(this);
        if (Build.VERSION.SDK_INT >= 21) {
            View decroView = getWindow().getDecorView();
            decroView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);//布局显示在状态栏上面
            getWindow().setStatusBarColor(Color.TRANSPARENT);//设置颜色为透明色
        }

        final Intent intent = getIntent();
        String songName = intent.getStringExtra("songName");
        singerName = intent.getStringExtra("singerName");
        String songmid = intent.getStringExtra("mid");
        final String alumnId = intent.getStringExtra("alumnId");
        back = findViewById(R.id.play_back);
        songsName = findViewById(R.id.play_name);
        songsName.setText(songName);
        singer = findViewById(R.id.play_singer);
        singer.setText(singerName);
        start = findViewById(R.id.play_start);
        seekBar = findViewById(R.id.seekbar);
        image = findViewById(R.id.pp_image);
        show = findViewById(R.id.show_word);

        seekBar.setOnSeekBarChangeListener(new SeekBarChangeEvent());
        InitImage(alumnId);

        InitPlay(songmid);
        InitView(songmid);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    start.setBackgroundResource(R.drawable.start);
                }else{
                    mediaPlayer.pause();
                    start.setBackgroundResource(R.drawable.pause);
                }
            }
        });

    }


    private void showUI(final Bitmap bitmap) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(bitmap != null)
                    image.setImageBitmap(bitmap);
            }
        });
    }
    public void InitPlay(String songmid){
        try {
            final String palyAddress = "https://u.y.qq.com/cgi-bin/musicu.fcg?format=json&data=%7B%22req_0%22%3A%7B%22module%22%3A%22vkey.GetVkeyServer%22%2C%22method%22%3A%22CgiGetVkey%22%2C%22param%22%3A%7B%22guid%22%3A%22358840384%22%2C%22songmid%22%3A%5B%22"
                    + songmid + "%22%5D%2C%22songtype%22%3A%5B0%5D%2C%22uin%22%3A%221443481947%22%2C%22loginflag%22%3A1%2C%22platform%22%3A%2220%22%7D%7D%2C%22comm%22%3A%7B%22uin%22%3A%2218585073516%22%2C%22format%22%3A%22json%22%2C%22ct%22%3A24%2C%22cv%22%3A0%7D%7D";
            HttpUrl.sendHttpResult(palyAddress, new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    String data = response.body().string();
                    result = parseJSONWithJSON(data);
                    if(result.equals("http://ws.stream.qqmusic.qq.com/")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(PlayActivity.this,"该歌曲暂时没有版权，搜搜其它歌曲吧",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }else{
                        Message message = new Message();
                        message.what = UPDATE_TEXT;
                        message.obj = result;
                        handler.sendMessage(message);

                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void InitView(String songmid){
        String address = "https://c.y.qq.com/lyric/fcgi-bin/fcg_query_lyric_new.fcg?songmid=" + songmid + "&format=json&nobase64=1";
        GetHttpUrl.sendHttpResult(address, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String data = response.body().string();
                Log.i("Test",data);
                try {
                    ParseWithObject(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void ParseWithObject(String data) throws JSONException {
        JSONObject object = new JSONObject(data);

        String lyric = object.optString("lyric",null);

        String s2 = lyric.substring(lyric.indexOf("[offset:0]") + 10);
        String s3 = s2.replaceAll("\\d{2}:\\d{2}.\\d{2}"," ");
        String s4 = s3.replaceAll("\\[\\ \\]","\n");
        final String s5 = s4.replaceAll("&apos;","`");

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                show.setText(s5);
            }
        });
    }
    public String parseJSONWithJSON(String dataa){
        String result = null;
        String purl = null;
        String sip = null ;
        try {
            JSONObject objectAll = new JSONObject(dataa);
            final String req_0 = objectAll.optString("req_0",null);
            if(!TextUtils.isEmpty(req_0)){
                JSONObject object = new JSONObject(req_0);
                String data = object.optString("data",null);
                JSONObject trueObject = new JSONObject(data);
                JSONArray jsonArray = trueObject.getJSONArray("sip");
                sip =jsonArray.get(0).toString();
                JSONArray array = trueObject.getJSONArray("midurlinfo");
                for(int i = 0 ; i < array.length(); i ++){
                    JSONObject jsonObject =  array.getJSONObject(i);
                    purl = jsonObject.optString("purl",null);
                }
            }
            result = sip + purl;


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void InitMedia(String address){

        if(mediaPlayer == null){
            mediaPlayer = new MediaPlayer();
        }else{
            mediaPlayer.stop();
        }
        try{
            mediaPlayer.setDataSource(address);
            mediaPlayer.prepare();
            mediaPlayer.start();
            seekBar.setMax(mediaPlayer.getDuration());
            handler.sendEmptyMessage(UPDATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void InitImage(String alumnId){
        try {
            String addresss = "http://y.gtimg.cn/music/photo_new/T002R180x180M000" + alumnId + ".jpg";
            HttpUrl.sendHttpResult(addresss, new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    InputStream inputStream = response.body().byteStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    showUI(bitmap);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {

        }
        @Override
        public void onStartTrackingTouch(SeekBar arg0) {
//开始拖动进度条

        }
        @Override
        public void onStopTrackingTouch(SeekBar arg0) {
//停止拖动进度条

            mediaPlayer.seekTo(arg0.getProgress());

//将media进度设置为当前seekbar的进度

        }
    }

}
