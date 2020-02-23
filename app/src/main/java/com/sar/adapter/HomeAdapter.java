package com.sar.adapter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sar.R;
import com.sar.model.NewsModel;

import java.util.List;

import static com.sar.constant.Server.IMAGE_NEWS_URL;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>{

    private List<NewsModel> newsModels;
    private Context ctx;
    private long DURATION = 300;
    private boolean on_attach = true;

    public HomeAdapter(Context context, List<NewsModel> list){
        ctx = context;
        newsModels = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_horizontal, parent, false);

        return new ViewHolder(itemView);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout linearLayout;
        private TextView tvTitle, tvDate;
        private ImageView ivNews;

        public ViewHolder(View view) {
            super(view);
            linearLayout = view.findViewById(R.id.lyt_parent);
            tvTitle = view.findViewById(R.id.tv_title);
            tvDate = view.findViewById(R.id.tv_date);
            ivNews = view.findViewById(R.id.iv_news);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {

        try {
            Log.e(this.getClass().getName(), "onBindViewHolder list => " + newsModels.size());
            Log.e(this.getClass().getName(), "onBindViewHolder i => " + i);

            String title = newsModels.get(i).getTitle();
            String date = newsModels.get(i).getCreated_at();
            String image = newsModels.get(i).getImage();

            String subDate = date.substring(0, 10);

            setAnimation(holder.itemView, i);

            holder.tvTitle.setText(title);
            holder.tvDate.setText(subDate);

            Glide.with(ctx)
                    .load(IMAGE_NEWS_URL + image)
                    .apply(new RequestOptions().placeholder(R.drawable.image_thumb_12)
                            .fitCenter())
                    .into(holder.ivNews);

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                Log.d(this.getClass().getName(), "onScrollStateChanged: Called " + newState);
                on_attach = false;
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        super.onAttachedToRecyclerView(recyclerView);
    }

    private void setAnimation(View itemView, int i) {
        if(!on_attach){
            i = -1;
        }
        boolean not_first_item = i == -1;
        i = i + 1;
        itemView.setTranslationX(-400f);
        itemView.setAlpha(0.f);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(itemView, "translationX", -400f, 0);
        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(itemView, "alpha", 1.f);
        ObjectAnimator.ofFloat(itemView, "alpha", 0.f).start();
        animatorTranslateY.setStartDelay(not_first_item ? DURATION : (i * DURATION));
        animatorTranslateY.setDuration((not_first_item ? 2 : 1) * DURATION);
        animatorSet.playTogether(animatorTranslateY, animatorAlpha);
        animatorSet.start();
    }

    @Override
    public int getItemCount() {
        return newsModels.size();
    }
}
