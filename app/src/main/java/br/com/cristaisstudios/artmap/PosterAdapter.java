package br.com.cristaisstudios.artmap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import java.util.List;

public class PosterAdapter extends RecyclerView.Adapter<PosterAdapter.PosterViewHolder> {

    private Context context;
    private List<String> imageUrls;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(String imageUrl, int position);
    }

    public PosterAdapter(Context context, List<String> imageUrls, OnItemClickListener listener) {
        this.context = context;
        this.imageUrls = imageUrls;
        this.listener = listener;
    }

    @Override
    public PosterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_poster, parent, false);
        return new PosterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PosterViewHolder holder, int position) {
        String imageUrl = imageUrls.get(position);
        holder.bind(imageUrl, position, listener);
    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
    }

    public static class PosterViewHolder extends RecyclerView.ViewHolder {
        ImageView imagePoster;

        public PosterViewHolder(View itemView) {
            super(itemView);
            imagePoster = itemView.findViewById(R.id.imagePoster);
        }

        public void bind(String imageUrl, int position, OnItemClickListener listener) {
            Glide.with(itemView.getContext()).load(imageUrl).into(imagePoster);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(imageUrl, position);
                }
            });
        }
    }
}
