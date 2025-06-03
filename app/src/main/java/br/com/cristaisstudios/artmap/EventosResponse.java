package br.com.cristaisstudios.artmap;

import java.util.List;

public class EventosResponse {
    private boolean success;
    private String token;
    private String usuario;
    private String error;
    private List<Dados> dados;

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
    public List<Dados> getEventos() { return dados; }
}
