package com.sar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sar.R;
import com.sar.roomDB.FavoriteList;

import java.util.List;

import static com.sar.constant.Server.IMAGE_NEWS_URL;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private List<FavoriteList> favoriteLists;
    Context context;

    public FavoriteAdapter(List<FavoriteList> favoriteLists, Context context) {
        this.favoriteLists = favoriteLists;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_news_horizontal,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
      FavoriteList fl = favoriteLists.get(i);

      String image = fl.getImage();

//        Picasso.with(context).load(fl.getImage()).into(viewHolder.img);
      viewHolder.tvTittle.setText(fl.getName());
      viewHolder.tvDate.setText(fl.getDate());

      Glide.with(context)
        .load(IMAGE_NEWS_URL + image)
        .apply(new RequestOptions().placeholder(R.drawable.image_thumb_12)
                .fitCenter())
        .into(viewHolder.ivNews);

    }

    @Override
    public int getItemCount() {
        return favoriteLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivNews;
        TextView tvTittle, tvDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivNews = itemView.findViewById(R.id.iv_news);
            tvTittle = itemView.findViewById(R.id.tv_title);
            tvDate = itemView.findViewById(R.id.tv_date);
        }
    }
}
