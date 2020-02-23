//package com.sar.adapter;
//
//import android.animation.AnimatorSet;
//import android.animation.ObjectAnimator;
//import android.content.Context;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Filter;
//import android.widget.Filterable;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.sar.R;
//import com.sar.dao.NewsDao;
//import com.sar.model.NewsModel;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder> implements Filterable {
//
//    private List<NewsModel> listNews;
//    private List<NewsModel> newLokasiModelList;
//    private Context context;
//    private LocationAdapterListener locationAdapterListener;
//
//    private long DURATION = 100;
//    private boolean on_attach = true;
//
//    public LocationAdapter(Context ctx, List<NewsModel> list, LocationAdapterListener listener) {
//
//        this.context = ctx;
//        this.newLokasiModelList = list;
//        this.listNews = list;
//        this.locationAdapterListener = listener;
//
//        Log.e(this.getClass().getName(), "HastagAdapter list => "+newLokasiModelList.size());
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_fragmement_tag, parent, false);
//
//        return new ViewHolder(itemView);
//    }
//
//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                String charString = charSequence.toString();
//                Log.e(this.getClass().getName(), "getFilter : charSquence => "+charSequence);
//                if (charString.isEmpty()) {
//                    newLokasiModelList = listNews;
//                } else {
//                    List<NewsModel> filteredList = new ArrayList<>();
//                    for (NewsModel row : listNews) {
//
//                        // name match condition. this might differ depending on your requirement
//                        // here we are looking for name or phone number match
//                        if (row.getKeterangan().toLowerCase().contains(charString.toLowerCase()) || row.getKeterangan().contains(charSequence)) {
//                            filteredList.add(row);
//                            Log.e(this.getClass().getName(), "getFilter IF : row => "+row.getKeterangan());
//                        }
//                    }
//
//                    newLokasiModelList = filteredList;
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = newLokasiModelList;
//
//                Log.e(this.getClass().getName(), "getFilter : filteredResult => "+filterResults);
//
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                newLokasiModelList = (ArrayList<NewLokasiModel>) filterResults.values;
//                notifyDataSetChanged();
//            }
//        };
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        private TextView tvTag;
//
//        public ViewHolder(View view) {
//            super(view);
//            tvTag = view.findViewById(R.id.tv_tag);
//
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    // send selected contact in callback
//                    locationAdapterListener.onLocationSelected(newLokasiModelList.get(getAdapterPosition()));
//                }
//            });
//        }
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//
//        holder.setIsRecyclable(false);
//
//        String location = newLokasiModelList.get(position).getKeterangan();
//
//        holder.tvLocation.setText(location);
//        setAnimation(holder.itemView, position);
//        Log.e(this.getClass().getName(), "onBindViewHolder : location => "+location);
//    }
//
//    @Override
//    public int getItemCount() {
//        return newLokasiModelList.size();
//    }
//
//    @Override
//    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                Log.d(this.getClass().getName(), "onScrollStateChanged: Called " + newState);
//                on_attach = false;
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//        });
//
//        super.onAttachedToRecyclerView(recyclerView);
//    }
//
//    private void setAnimation(View itemView, int i) {
//        if(!on_attach){
//            i = -1;
//        }
//        boolean not_first_item = i == -1;
//        i = i + 1;
//        itemView.setTranslationX(-400f);
//        itemView.setAlpha(0.f);
//        AnimatorSet animatorSet = new AnimatorSet();
//        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(itemView, "translationX", -400f, 0);
//        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(itemView, "alpha", 1.f);
//        ObjectAnimator.ofFloat(itemView, "alpha", 0.f).start();
//        animatorTranslateY.setStartDelay(not_first_item ? DURATION : (i * DURATION));
//        animatorTranslateY.setDuration((not_first_item ? 2 : 1) * DURATION);
//        animatorSet.playTogether(animatorTranslateY, animatorAlpha);
//        animatorSet.start();
//    }
//
//    public interface LocationAdapterListener {
//        void onLocationSelected(NewLokasiModel newLokasiModel);
//    }
//
//}
//
//
