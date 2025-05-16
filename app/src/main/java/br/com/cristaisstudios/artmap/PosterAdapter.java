package br.com.cristaisstudios.artmap;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import java.util.List;

public class PosterAdapter extends RecyclerView.Adapter<PosterAdapter.PosterViewHolder> {

    private Context context;
    private List<Evento> eventos;
    public TextView txtdescricao;
    boolean mostratexto,mostradesc=false;
    private int selectedPosition = RecyclerView.NO_POSITION;
    private int previo=0;

    public PosterAdapter(Context context, List<Evento> eventos, TextView txtdescricao) {
        this.context = context;
        this.eventos = eventos;
        this.txtdescricao = txtdescricao;
    }

    @Override
    public PosterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_poster, parent, false);
        return new PosterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PosterViewHolder holder, int position) {
        Evento evento = eventos.get(position);
        holder.bind(evento, position, txtdescricao);
    }

    @Override
    public int getItemCount() {
        return eventos.size();
    }

    public static class PosterViewHolder extends RecyclerView.ViewHolder {
        ImageView imagePoster;
        TextView txtTitulo;

        public PosterViewHolder(View itemView) {
            super(itemView);
            imagePoster = itemView.findViewById(R.id.imagePoster);
            txtTitulo = itemView.findViewById(R.id.evento_titulo);
        }

        public void bind(Evento evento, int position, TextView descricao) {
            Glide.with(itemView.getContext()).load(evento.getImagem()).into(imagePoster);
            txtTitulo.setText(evento.getTitulo());
            itemView.setOnClickListener(v -> {
                boolean isVisible = (txtTitulo.getVisibility() == View.VISIBLE);
                descricao.setText(evento.getDescricao());
                txtTitulo.setVisibility(isVisible ? View.GONE : View.VISIBLE);
                descricao.setVisibility(isVisible ? View.GONE : View.VISIBLE);
            });
        }
    }
}
