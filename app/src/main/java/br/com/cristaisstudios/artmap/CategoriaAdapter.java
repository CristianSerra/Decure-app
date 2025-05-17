package br.com.cristaisstudios.artmap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.CategoriaViewHolder> {

    private List<Categoria> lista;
    private OnCategoriaClickListener listener;

    public CategoriaAdapter(List<Categoria> lista, OnCategoriaClickListener listener) {
        this.lista = lista;
        this.listener = listener;
    }

    public interface OnCategoriaClickListener {
        void onCategoriaClick(Categoria categoria);
    }

    @NonNull
    @Override
    public CategoriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_categoria, parent, false);
        return new CategoriaViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriaViewHolder holder, int position) {
        Categoria categoria = lista.get(position);
        holder.txtCategoria.setText(categoria.getNome());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCategoriaClick(categoria);
            }
        });

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class CategoriaViewHolder extends RecyclerView.ViewHolder {
        TextView txtCategoria;

        public CategoriaViewHolder(View itemView) {
            super(itemView);
            txtCategoria = itemView.findViewById(R.id.txtCategoria);
        }
    }
}
