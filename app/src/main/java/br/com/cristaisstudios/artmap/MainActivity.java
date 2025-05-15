package br.com.cristaisstudios.artmap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
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
    private ImageButton btnGoToLogin;

    List<Dados> eventos;
    RecyclerView CatNovidades, CatExposicoes, CatGalerias;
    TextView username, txtdesc1, txtdesc2, txtdesc3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE);
        String usertoken = prefs.getString("AUTH_TOKEN", null);
        //username = findViewById(R.id.usuario);
        //username.setText(usertoken);

        btnGoToLogin = findViewById(R.id.btnPerfil);
        btnGoToLogin.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        CatNovidades = findViewById(R.id.Novidades);
        CatExposicoes = findViewById(R.id.Exposicoes);
        CatGalerias = findViewById(R.id.Galerias);
        txtdesc1 = findViewById(R.id.descricao1);
        txtdesc2 = findViewById(R.id.descricao2);
        txtdesc3 = findViewById(R.id.descricao3);

        List<Evento> lancamentos = new ArrayList<>();
        List<Evento> exposicoes = new ArrayList<>();
        List<Evento> galerias   = new ArrayList<>();

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
                            lancamentos.add(new Evento(info.getTitulo(),pasta+info.getimagem(),info.getDescricao()));
                        }
                        if (aux>6 && aux<=9) {
                            exposicoes.add(new Evento(info.getTitulo(),pasta+info.getimagem(),info.getDescricao()));
                        }
                        if (aux>9 && aux<=12) {
                            galerias.add(new Evento(info.getTitulo(),pasta+info.getimagem(),info.getDescricao()));
                        }
                    }
                    setupRecycler(CatNovidades, lancamentos, txtdesc1);
                    setupRecycler(CatExposicoes, exposicoes, txtdesc2);
                    setupRecycler(CatGalerias, galerias, txtdesc3);
                }
            }

            @Override
            public void onFailure(Call<List<Dados>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Falha: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupRecycler(RecyclerView recyclerView, List<Evento> data, TextView txtdescricao) {

        PosterAdapter adapter = new PosterAdapter(this, data, txtdescricao);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
