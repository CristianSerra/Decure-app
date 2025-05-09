package br.com.cristaisstudios.artmap;

public class LoginResponse {
    private boolean success;  // adapte ao que sua API retorna
    private String token;
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

}

