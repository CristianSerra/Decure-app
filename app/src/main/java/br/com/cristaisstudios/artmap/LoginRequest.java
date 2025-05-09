package br.com.cristaisstudios.artmap;

public class LoginRequest {
    private String Email;
    private String Senha;

    public LoginRequest(String email, String senha) {
        this.Email = email;
        this.Senha = senha;
    }
}
