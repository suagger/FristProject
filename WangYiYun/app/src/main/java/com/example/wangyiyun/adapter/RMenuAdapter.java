package com.example.wangyiyun.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wangyiyun.R;

import java.util.List;

public class RMenuAdapter extends RecyclerView.Adapter<RMenuAdapter.ViewHolder> {
  private List<SongMenu> llist;

    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView name;
        public ViewHolder(@NonNull View view) {
            super(view);
            imageView = view.findViewById(R.id.menu_image);
            name = view.findViewById(R.id.menu_name);
        }
    }

    public RMenuAdapter(List<SongMenu> mm){
        llist = mm;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SongMenu menu = llist.get(position);
        holder.imageView.setImageResource(menu.getImageId());
        holder.name.setText(menu.getName());

    }

    @Override
    public int getItemCount() {
        return llist.size();
    }


}
