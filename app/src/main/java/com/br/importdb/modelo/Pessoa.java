package com.br.importdb.modelo;

public class Pessoa {
    private int idPessoa;
    private String nomePessoa;
    private String sobrenomePessoa;

    public Pessoa(){

    }

    public Pessoa(int idPessoa, String nomePessoa) {
        this.idPessoa = idPessoa;
        this.nomePessoa = nomePessoa;
    }

    public int getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(int idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getNomePessoa() {
        return nomePessoa;
    }

    public void setNomePessoa(String nomePessoa) {
        this.nomePessoa = nomePessoa;
    }

    public String getSobrenomePessoa() {
        return sobrenomePessoa;
    }

    public void setSobrenomePessoa(String sobrenomePessoa) {
        this.sobrenomePessoa = sobrenomePessoa;
    }

    @Override
    public String toString() {
        return nomePessoa + sobrenomePessoa ;
    }
}
