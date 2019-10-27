package com.example.wangyiyun.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> fragmentList;
    public ViewPagerAdapter(FragmentManager fm,List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }
//显示第几页
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }
//总共的页面数
    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
