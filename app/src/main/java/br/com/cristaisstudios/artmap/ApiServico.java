package br.com.cristaisstudios.artmap;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiServico {
    @GET("carrega.php")
    Call<List<Dados>> getdados();

    @FormUrlEncoded
    @POST("/userlogin.php")
    Call<LoginResponse> login(
            @Field("Email") String email,
            @Field("Senha") String senha
    );

}
