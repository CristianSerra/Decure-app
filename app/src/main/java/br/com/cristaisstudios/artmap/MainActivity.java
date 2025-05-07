package br.com.cristaisstudios.artmap;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String pasta = "https://artmap.ok.etc.br/images/";

    List<Dados> eventos;
    RecyclerView CatNovidades, CatExposicoes, CatGalerias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CatNovidades = findViewById(R.id.Novidades);
        CatExposicoes = findViewById(R.id.Exposicoes);
        CatGalerias = findViewById(R.id.Galerias);

        List<String> lancamentos = new ArrayList<>();
        List<String> exposicoes = new ArrayList<>();
        List<String> galerias   = new ArrayList<>();

        ApiServico apiService = ApiClient.getRetrofit().create(ApiServico.class);
        Call<List<Dados>> call = apiService.getdados();

        call.enqueue(new Callback<List<Dados>>() {
            @Override
            public void onResponse(Call<List<Dados>> call, Response<List<Dados>> response) {
                if (response.isSuccessful()) {
                    eventos = response.body();
                    int aux=0;
                    for (Dados info : eventos) {
                        aux++;
                        if (aux>3 && aux<=6) {
                            lancamentos.add(pasta+info.getimagem());
                        }
                        if (aux>6 && aux<=9) {
                            exposicoes.add(pasta+info.getimagem());
                        }
                        if (aux>9 && aux<=12) {
                            galerias.add(pasta+info.getimagem());
                        }
                    }
                    setupRecycler(CatNovidades, lancamentos);
                    setupRecycler(CatExposicoes, exposicoes);
                    setupRecycler(CatGalerias, galerias);
                }
            }

            @Override
            public void onFailure(Call<List<Dados>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Falha: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupRecycler(RecyclerView recyclerView, List<String> data) {
        String recyclerName = getResources().getResourceEntryName(recyclerView.getId());
        TextView txtTitulo = findViewById(R.id.Titulo);
        TextView txtDesc = findViewById(R.id.Descricao);
        PosterAdapter adapter = new PosterAdapter(this, data, new PosterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String imageUrl, int position) {
                int auxpos=3;
                if (recyclerName.equals("Exposicoes")) auxpos=6;
                if (recyclerName.equals("Galerias")) auxpos=9;
                Dados saida = eventos.get(auxpos+position);
                txtDesc.setText("Descrição: "+saida.getDescricao());
                //txtTitulo.setText(recyclerName+" : "+saida.getTitulo());

            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
