package com.example.hp.thekleaners;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hp.thekleaners.pojoClass.ForCarService;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<ForCarService> profiles;

    public MyAdapter(Context c, ArrayList<ForCarService> p) {
        this.context = c;
        this.profiles = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.list_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.name.setText(profiles.get(position).getCarName());
        holder.email.setText(profiles.get(position).getCarNumber());

    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, email;


        MyViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.post_title);
            email = (TextView) itemView.findViewById(R.id.post_desc);
        }
    }
}
