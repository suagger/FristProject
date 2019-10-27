package com.example.wangyiyun;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class main {

    public static List<Activity> activityList = new ArrayList<>();
    public void exit(){
        for(Activity activity : activityList){
            activity.finish();
        }
        System.exit(0);
    }

}

