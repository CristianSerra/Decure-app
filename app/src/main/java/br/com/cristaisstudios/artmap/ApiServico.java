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
    @POST("userlogin.php")
    Call<LoginResponse> login(
            @Field("Email") String Email,
            @Field("Senha") String Senha
    );

    @FormUrlEncoded
    @POST("usuarios.php")
    Call<Void> cadastrarUsuario(
            @Field("nome") String nome,
            @Field("email") String email,
            @Field("cpf") String cpf,
            @Field("senha") String senha,
            @Field("telefone") String telefone,
            @Field("dtnascimento") String dtnascimento,
            @Field("logradouro") String logradouro,
            @Field("complemento") String complemento,
            @Field("bairro") String bairro,
            @Field("localidade") String localidade,
            @Field("uf") String uf
    );

}
