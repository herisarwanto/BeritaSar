package com.sar.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sar.R;
import com.sar.model.CommentModel;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{

    private List<CommentModel> commentModelList;
    private Context context;

    public CommentAdapter(Context ctx, List<CommentModel> list) {

        this.context = ctx;
        this.commentModelList = list;

        Log.e(this.getClass().getName(), "CommentAdapter : list => "+commentModelList.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item1_comment, parent, false);

        return new ViewHolder(itemView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvMessage;

        public ViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tv_name);
            tvMessage = view.findViewById(R.id.tv_message);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String username = commentModelList.get(position).getUsername();
        String message = commentModelList.get(position).getComment();

        holder.tvMessage.setText(message);
        holder.tvName.setText(username);
    }

    @Override
    public int getItemCount() {
        return commentModelList.size();
    }
}
