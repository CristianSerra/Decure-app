package br.com.cristaisstudios.artmap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String pasta = "https://artmap.ok.etc.br/images/";
    private ImageButton btnGoToLogin;
    String user, email, token;

    EventosResponse resposta;
    List<Dados> eventos;
    LinearLayout blocoNV, blocoEX, blocoMU, blocoED, blocoGA;
    RecyclerView CatNovidades, CatExposicoes, CatGalerias, CatMuseus, CatEditais;
    RecyclerView recyclerCategorias;
    TextView username, txtdesc1, txtdesc2, txtdesc3, txtdesc4, txtdesc5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        blocoNV = findViewById(R.id.categoriaNV);
        blocoEX = findViewById(R.id.categoriaEX);
        blocoMU = findViewById(R.id.categoriaMU);
        blocoED = findViewById(R.id.categoriaED);
        blocoGA = findViewById(R.id.categoriaGA);

        CatNovidades = findViewById(R.id.Novidades);
        CatExposicoes = findViewById(R.id.Exposicoes);
        CatMuseus = findViewById(R.id.Museus);
        CatEditais = findViewById(R.id.Editais);
        CatGalerias = findViewById(R.id.Galerias);
        txtdesc1 = findViewById(R.id.descricao1);
        txtdesc2 = findViewById(R.id.descricao2);
        txtdesc3 = findViewById(R.id.descricao3);
        txtdesc4 = findViewById(R.id.descricao4);
        txtdesc5 = findViewById(R.id.descricao5);

        recyclerCategorias = findViewById(R.id.recyclerCategorias);

        List<Categoria> categorias = Arrays.asList(
                new Categoria("Novidades"),
                new Categoria("Exposições"),
                new Categoria("Galerias"),
                new Categoria("Museus"),
                new Categoria("Leilões"),
                new Categoria("Feiras"),
                new Categoria("Editais"),
                new Categoria("Eventos")
        );

        CategoriaAdapter adapter = new CategoriaAdapter(categorias, categoria -> {
            boolean visivel;
            if (categoria.getNome().equals("Novidades")) {
                visivel = (blocoNV.getVisibility() == View.VISIBLE);
                blocoNV.setVisibility(visivel ? View.GONE : View.VISIBLE);
            }
            if (categoria.getNome().equals("Exposições")) {
                visivel = (blocoEX.getVisibility() == View.VISIBLE);
                blocoEX.setVisibility(visivel ? View.GONE : View.VISIBLE);
            }
            if (categoria.getNome().equals("Museus")) {
                visivel = (blocoMU.getVisibility() == View.VISIBLE);
                blocoMU.setVisibility(visivel ? View.GONE : View.VISIBLE);
            }
            if (categoria.getNome().equals("Editais")) {
                visivel = (blocoED.getVisibility() == View.VISIBLE);
                blocoED.setVisibility(visivel ? View.GONE : View.VISIBLE);
            }
            if (categoria.getNome().equals("Galerias")) {
                visivel = (blocoGA.getVisibility() == View.VISIBLE);
                blocoGA.setVisibility(visivel ? View.GONE : View.VISIBLE);
            }
        });

        recyclerCategorias.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerCategorias.setAdapter(adapter);

        SharedPreferences prefs = getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE);
        user = prefs.getString("AUTH_USER", null);
        email = prefs.getString("AUTH_EMAIL", null);
        token = prefs.getString("AUTH_TOKEN", null);

        username = findViewById(R.id.usuario);
        if (user != null) {
            username.setText(user);
            blocoNV.setVisibility(View.VISIBLE);
        }

        btnGoToLogin = findViewById(R.id.btnPerfil);
        btnGoToLogin.setOnClickListener(v -> {
            if (user != null) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.apply();
                user = prefs.getString("AUTH_USER", null);
                username.setText("não logado");
                blocoNV.setVisibility(View.GONE);
                blocoEX.setVisibility(View.GONE);
                blocoMU.setVisibility(View.GONE);
                blocoED.setVisibility(View.GONE);
                blocoGA.setVisibility(View.GONE);
                setupRecycler(CatNovidades, new ArrayList<>(), txtdesc1);
                setupRecycler(CatExposicoes, new ArrayList<>(), txtdesc2);
                setupRecycler(CatMuseus, new ArrayList<>(), txtdesc3);
                setupRecycler(CatEditais, new ArrayList<>(), txtdesc4);
                setupRecycler(CatGalerias, new ArrayList<>(), txtdesc5);
            } else {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        List<Evento> novidades = new ArrayList<>();
        List<Evento> exposicoes = new ArrayList<>();
        List<Evento> galerias = new ArrayList<>();
        List<Evento> museus = new ArrayList<>();
        List<Evento> editais = new ArrayList<>();

        if (email != null && token != null) {
            ApiServico apiService = ApiClient.getRetrofit().create(ApiServico.class);
            Call<EventosResponse> call = apiService.getdados(email, token);

            call.enqueue(new Callback<EventosResponse>() {
                @Override
                public void onResponse(Call<EventosResponse> call, Response<EventosResponse> response) {
                    if (response.isSuccessful()) {
                        resposta = response.body();
                        eventos = resposta.getEventos();
                        for (Dados info : eventos) {
                            if (info.getCategoria().equals("NV")) {
                                novidades.add(new Evento(info.getTitulo(), pasta + info.getimagem(), info.getDescricao()));
                            }
                            if (info.getCategoria().equals("EX")) {
                                exposicoes.add(new Evento(info.getTitulo(), pasta + info.getimagem(), info.getDescricao()));
                            }
                            if (info.getCategoria().equals("MU")) {
                                museus.add(new Evento(info.getTitulo(), pasta + info.getimagem(), info.getDescricao()));
                            }
                            if (info.getCategoria().equals("ED")) {
                                editais.add(new Evento(info.getTitulo(), pasta + info.getimagem(), info.getDescricao()));
                            }
                            if (info.getCategoria().equals("GA")) {
                                galerias.add(new Evento(info.getTitulo(), pasta + info.getimagem(), info.getDescricao()));
                            }
                        }
                        setupRecycler(CatNovidades, novidades, txtdesc1);
                        setupRecycler(CatExposicoes, exposicoes, txtdesc2);
                        setupRecycler(CatMuseus, museus, txtdesc3);
                        setupRecycler(CatEditais, editais, txtdesc4);
                        setupRecycler(CatGalerias, galerias, txtdesc5);
                    }
                }

                @Override
                public void onFailure(Call<EventosResponse> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Falha: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setupRecycler(RecyclerView recyclerView, List<Evento> data, TextView txtdescricao) {

        PosterAdapter adapter = new PosterAdapter(this, data, txtdescricao);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
