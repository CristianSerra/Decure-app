package br.com.cristaisstudios.artmap;

public class Usuario {
    public String nome, email, cpf, senha, telefone, dtnascimento;
    public String logradouro, complemento, bairro, localidade, uf;

    public Usuario(String nome, String email, String cpf, String senha, String telefone,
                   String dtnascimento, String logradouro, String complemento,
                   String bairro, String localidade, String uf) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.senha = senha;
        this.telefone = telefone;
        this.dtnascimento = dtnascimento;
        this.logradouro = logradouro;
        this.complemento = complemento;
        this.bairro = bairro;
        this.localidade = localidade;
        this.uf = uf;
    }
}
