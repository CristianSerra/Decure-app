package br.com.cristaisstudios.artmap;

public class LoginResponse {
    private boolean success;
    private String token;
    private String usuario;
    private String error;

    public boolean isSuccess() {
        return success;
    }
    public String getToken() {
        return token;
    }
    public String getError() {
        return error;
    }
    public String getUser() { return usuario; }
}

