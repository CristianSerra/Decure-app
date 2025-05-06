package br.com.cristaisstudios.artmap;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiServico {
    @GET("carrega.php")
    Call<List<Dados>> getdados();
}
