package edu.northeastern.numad23sp_shizeng;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UrlAdapter extends RecyclerView.Adapter<UrlAdapter.UrlViewHolder> {
    private final List<Url> data;
    private final Context context;

    public UrlAdapter(List<Url> data, Context context) {
        this.data = data;
        this.context = context;
    }


    @NonNull
    @Override
    public UrlViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UrlViewHolder(LayoutInflater.from(context).inflate(R.layout.url_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull UrlViewHolder holder, int position) {
        holder.bindThisData(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public List<Url> getData() {
        return data;
    }

    public void addData(int position, Url url) {
        data.add(position, url);
        notifyItemInserted(position);
    }


    @SuppressLint("NotifyDataSetChanged")
    public void removeData(int position) {
        data.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public static class UrlViewHolder extends RecyclerView.ViewHolder {
        public TextView urlTV;

        public UrlViewHolder(@NonNull View itemView) {
            super(itemView);
            this.urlTV = itemView.findViewById(R.id.tv_UrlItem);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (myRecyclerItemClickListener != null) {
                        myRecyclerItemClickListener.OnRecyclerItemClick(getAdapterPosition());
                    }
                }
            });
        }

        public void bindThisData(Url url) {
            System.out.println("url is " + url.getUrl());
            urlTV.setText(url.getUrl());
        }
    }

    public interface OnRecyclerItemClickListener {
        void OnRecyclerItemClick(int pos);
    }

    public static OnRecyclerItemClickListener myRecyclerItemClickListener;

    public void setRecyclerItemClickListener(OnRecyclerItemClickListener listener) {
        myRecyclerItemClickListener = listener;
    }
}
