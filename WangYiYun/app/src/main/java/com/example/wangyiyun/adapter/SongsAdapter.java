package com.example.wangyiyun.adapter;

import android.content.Intent;
import android.media.MediaPlayer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.wangyiyun.PlayActivity;
import com.example.wangyiyun.R;
import com.example.wangyiyun.fragment.Mine;
import com.example.wangyiyun.litepal.Information;
import java.util.List;



public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.ViewHolder> {
    List<SearchSongs> mList;
    public SongsAdapter(List<SearchSongs> list) {
        mList = list;
    }

    @NonNull
    @Override
    public SongsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.songs_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SongsAdapter.ViewHolder holder, final int position) {
        final SearchSongs songs = mList.get(position);
        holder.singerName.setText(songs.getSingerName());
        holder.songName.setText(songs.getSongsName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PlayActivity.class);
//                Intent intent = new Intent(v.getContext(), Mine.class);
                intent.putExtra("singerName",songs.getSingerName());
                intent.putExtra("songName",songs.getSongsName());
                intent.putExtra("alumnId",songs.getAlumnId());
                intent.putExtra("mid",songs.getMid());
                Information information = new Information();
                information.setSongsName(songs.getSongsName());
                information.setSingerName(songs.getSingerName());
                information.setMid(songs.getMid());
                information.setAlumnId(songs.getAlumnId());
                information.save();
                v.getContext().startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView songName;
        TextView singerName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            songName = itemView.findViewById(R.id.songs_name);
            singerName = itemView.findViewById(R.id.singer);
        }
    }
}
