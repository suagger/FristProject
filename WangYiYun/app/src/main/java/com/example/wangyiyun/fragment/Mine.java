package com.example.wangyiyun.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wangyiyun.PlayActivity;
import com.example.wangyiyun.R;
import com.example.wangyiyun.adapter.RMenuAdapter;
import com.example.wangyiyun.adapter.SongMenu;

import java.util.ArrayList;
import java.util.List;

public class Mine extends Fragment {

    private List<SongMenu> menuList = new ArrayList<>();
    private TextView recently;
    private TextView now;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mine_fragment,null);
        InitsMenu();
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        RMenuAdapter adapter1 = new RMenuAdapter(menuList);
        recyclerView.setAdapter(adapter1);
        recently = view.findViewById(R.id.recently);
        now = view.findViewById(R.id.now_play);
        recently.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),Show.class);
                v.getContext().startActivity(intent);
            }
        });
        now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PlayActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
    private void InitsMenu(){
        SongMenu menu = new SongMenu(R.drawable.liang,"云村正能量");
        menuList.add(menu);
        SongMenu menu1 = new SongMenu(R.drawable.dao,"亲子频道");
        menuList.add(menu1);
        SongMenu menu2 = new SongMenu(R.drawable.fm,"私人FM");
        menuList.add(menu2);
        SongMenu menu3 = new SongMenu(R.drawable.yin,"最嗨电音");
        menuList.add(menu3);
        SongMenu menu4 = new SongMenu(R.drawable.jian,"Sati空间");
        menuList.add(menu4);
        SongMenu menu5 = new SongMenu(R.drawable.tui,"私藏推荐");
        menuList.add(menu5);
        SongMenu menu6 = new SongMenu(R.drawable.you,"因乐交友");
        menuList.add(menu6);
        SongMenu menu7 = new SongMenu(R.drawable.qu,"古典专区");
        menuList.add(menu7);
        SongMenu menu8 = new SongMenu(R.drawable.bu,"跑步FM");
        menuList.add(menu8);
        SongMenu menu9 = new SongMenu(R.drawable.tai,"小冰电台");
        menuList.add(menu9);
        SongMenu menu10 = new SongMenu(R.drawable.jiushi,"爵士电台");
        menuList.add(menu10);
        SongMenu menu11 = new SongMenu(R.drawable.shi,"驾驶模式");
        menuList.add(menu11);
        SongMenu menu12 = new SongMenu(R.drawable.ji,"编辑");
        menuList.add(menu12);
    }
}
