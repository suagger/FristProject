package com.example.wangyiyun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.wangyiyun.adapter.SongMenu;
import com.example.wangyiyun.adapter.ViewPagerAdapter;
import com.example.wangyiyun.fragment.Cluod;
import com.example.wangyiyun.fragment.Find;
import com.example.wangyiyun.fragment.Mine;
import com.example.wangyiyun.fragment.Video;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private BottomNavigationView navigationView;;
    private TextView mine;
    private ViewPager viewPager;
    private List<Fragment> list;
    private ViewPagerAdapter pagerAdapter;
    private TextView find;
    private TextView cloud;
    private TextView video;
    private Button search;
    private DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main.activityList.add(this);
        if(Build.VERSION.SDK_INT >= 21) {
            View decroView = getWindow().getDecorView();
            decroView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            |View.SYSTEM_UI_FLAG_LAYOUT_STABLE);//布局显示在状态栏上面
            getWindow().setStatusBarColor(Color.TRANSPARENT);//设置颜色为透明色
        }

        navigationView = findViewById(R.id.nav_bottom);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.off:
                        new main().exit();
                        break;
                }
                return true;
            }
        });
        search = findViewById(R.id.search);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);

        mine = findViewById(R.id.mine);
        find = findViewById(R.id.find);
        cloud = findViewById(R.id.cloud);
        video = findViewById(R.id.video);
        mine.setOnClickListener(this);
        find.setOnClickListener(this);
        cloud.setOnClickListener(this);
        video.setOnClickListener(this);
        search.setOnClickListener(this);

        viewPager = findViewById(R.id.main_viewpager);
        viewPager.setOnPageChangeListener(new MyPagerChangeListener());//页面切换的监听
        list = new ArrayList<>();
        list.add(new Mine());
        list.add(new Find());
        list.add(new Video());
        list.add(new Cluod());
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),list);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0);//打开时，默认当前界面为0页面
        mine.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//对应标题栏的字体设置为粗体

        setSupportActionBar(toolbar);
        NavigationView navView = findViewById(R.id.nav_view);
        ActionBar bar = getSupportActionBar();
        if(bar != null){
            bar.setDisplayHomeAsUpEnabled(true);
            bar.setHomeAsUpIndicator(R.drawable.biao);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mine:
                viewPager.setCurrentItem(0);
                mine.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                find.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                cloud.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                video.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                break;
            case R.id.find:
                viewPager.setCurrentItem(1);
                find.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                mine.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                cloud.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                video.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                break;
            case R.id.cloud:
                viewPager.setCurrentItem(2);
                cloud.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                find.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                mine.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                video.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                break;
            case R.id.video:
                viewPager.setCurrentItem(3);
                video.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                find.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                cloud.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                mine.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                break;
            case R.id.search:
                Intent intent = new Intent(MainActivity.this,Search.class);
                startActivity(intent);
                default:
                    break;
        }
    }

    class MyPagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override

        //当页面在滑动的时候会调用此方法
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        //页面跳转完后得到调用
        @Override
        public void onPageSelected(int position) {
            switch (position){
                case 0:
                    mine.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    find.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    cloud.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    video.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    break;
                case 1:
                    find.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    mine.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    cloud.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    video.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    break;
                case 2:
                    cloud.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    find.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    mine.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    video.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    break;
                case 3:
                    video.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    find.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    cloud.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    mine.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    break;
                    default:
                        break;
            }
        }
        //当viewPager状态改变时候的代码块：
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
