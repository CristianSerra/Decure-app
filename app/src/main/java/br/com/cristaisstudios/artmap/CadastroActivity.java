package br.com.cristaisstudios.artmap;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroActivity extends AppCompatActivity {

    EditText etNome, etEmail, etCPF, etSenha, etTelefone, etDTNascimento,
            etLogradouro, etComplemento, etBairro, etLocalidade, etUF;
    Button btnCadastrar,btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro);

        etNome = findViewById(R.id.etNome);
        etEmail = findViewById(R.id.etEmail);
        etCPF = findViewById(R.id.etCPF);
        etSenha = findViewById(R.id.etSenha);
        etTelefone = findViewById(R.id.etTelefone);
        etDTNascimento = findViewById(R.id.etDTNascimento);
        etLogradouro = findViewById(R.id.etLogradouro);
        etComplemento = findViewById(R.id.etComplemento);
        etBairro = findViewById(R.id.etBairro);
        etLocalidade = findViewById(R.id.etLocalidade);
        etUF = findViewById(R.id.etUF);
        btnCadastrar = findViewById(R.id.btnCadastrar);
        btnVoltar = findViewById(R.id.btnVoltar);

        btnVoltar.setOnClickListener(v -> {
            Intent intent = new Intent(CadastroActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        btnCadastrar.setOnClickListener(v -> {
            ApiServico api = ApiClient.getRetrofit().create(ApiServico.class);
            Call<LoginResponse> call = api.cadastrarUsuario(
                    etNome.getText().toString(),
                    etEmail.getText().toString(),
                    etCPF.getText().toString(),
                    etSenha.getText().toString(),
                    etTelefone.getText().toString(),
                    etDTNascimento.getText().toString(),
                    etLogradouro.getText().toString(),
                    etComplemento.getText().toString(),
                    etBairro.getText().toString(),
                    etLocalidade.getText().toString(),
                    etUF.getText().toString()
            );

            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(CadastroActivity.this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(CadastroActivity.this, "Erro: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(CadastroActivity.this, "Falha: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });
    }
}
