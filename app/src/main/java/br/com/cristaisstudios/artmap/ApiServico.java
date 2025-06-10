package br.com.cristaisstudios.artmap;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiServico {
    @FormUrlEncoded
    @POST("eventos.php")
    Call<EventosResponse> getdados(
            @Field("Email") String Email,
            @Field("Token") String Token
    );

    @FormUrlEncoded
    @POST("userlogin.php")
    Call<LoginResponse> login(
            @Field("Email") String Email,
            @Field("Senha") String Senha
    );

    @FormUrlEncoded
    @POST("cadusuario.php")
    Call<LoginResponse> cadastrarUsuario(
            @Field("Nome") String nome,
            @Field("Email") String email,
            @Field("CPF") String cpf,
            @Field("Senha") String senha,
            @Field("Telefone") String telefone,
            @Field("DTNascimento") String dtnascimento,
            @Field("logradouro") String logradouro,
            @Field("complemento") String complemento,
            @Field("bairro") String bairro,
            @Field("localidade") String localidade,
            @Field("uf") String uf
    );

}
