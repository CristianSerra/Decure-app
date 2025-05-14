package br.com.cristaisstudios.artmap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button botaoLogin, botaoVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        // Recupera os elementos da UI
        emailEditText = findViewById(R.id.edEmail);
        passwordEditText = findViewById(R.id.edPassword);
        botaoLogin = findViewById(R.id.btnLogin);
        botaoVoltar = findViewById(R.id.btnVoltar);

        botaoVoltar.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // Ação de clique
        botaoLogin.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String senha = passwordEditText.getText().toString().trim();

            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            ApiServico api = ApiClient.getRetrofit().create(ApiServico.class);
            Call<LoginResponse> call = api.login(email, senha);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        LoginResponse resposta = response.body();
                        if (resposta.isSuccess()) {
                            String token = resposta.getToken();
                            Toast.makeText(LoginActivity.this, "Login realizado com sucesso - token: "+token, Toast.LENGTH_SHORT).show();
                            SharedPreferences prefs = getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("AUTH_TOKEN", token);
                            editor.apply();

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            String erro = response.body().getError();
                            Toast.makeText(LoginActivity.this, "Erro: "+erro, Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(LoginActivity.this, "Resposta inválida do servidor", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Erro de conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}

