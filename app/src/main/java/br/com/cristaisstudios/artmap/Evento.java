package br.com.cristaisstudios.artmap;

public class Evento {
    private String Titulo;
    private String imagem;
    private String descricao;

    public Evento(String titulo, String imagem, String descricao) {
        this.Titulo = titulo;
        this.descricao = descricao;
        this.imagem = imagem;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getImagem() {
        return imagem;
    }

    public String getTitulo() {
        return Titulo;
    }
}
