package com.example.cat_app_task3;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.CatViewHolder> {
    private ArrayList<CatModel> catModelArrayList;

    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_favourites, parent, false);
        CatViewHolder catViewHolder = new CatViewHolder(view);
        return catViewHolder;
    }

    public void onBindViewHolder(@NonNull final CatViewHolder holder, final int position) {
        final CatModel catObject = catModelArrayList.get(position);
        final Context context = holder.view.getContext();
        holder.catName.setText(catObject.getName());
        holder.view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(context, ActivityDetail.class);
                intent1.putExtra("id", catObject.getId());
                for(int i = 0; i < MainActivity.favouritesCatIdArrayList.size(); i++){
                    if(catObject.getId().equals(MainActivity.favouritesCatIdArrayList.get(i))){
                        intent1.putExtra("favouriteStatus", 1);
                    }
                }
                context.startActivity(intent1);
            }
        });
    }

    public int getItemCount() {
        return catModelArrayList.size();
    }

    public void setData(ArrayList<CatModel> data) {
        this.catModelArrayList = data;
    }

    public static class CatViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView catName;

        public CatViewHolder(View v) {
            super(v);
            view = v;
            catName = v.findViewById(R.id.catName);
        }
    }
}
