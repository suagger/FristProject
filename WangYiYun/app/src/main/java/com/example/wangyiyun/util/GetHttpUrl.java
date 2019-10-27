package com.example.wangyiyun.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class GetHttpUrl {
    public static void sendHttpResult(String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).addHeader("Referer","https://y.qq.com/portal/player.html").build();
        client.newCall(request).enqueue(callback);
    }
}
