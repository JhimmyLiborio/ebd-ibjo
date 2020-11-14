package com.registro.componentes.ibjo.entidade;

public class Aluno {



    private String pessoaID;
    private int  matricula;
    private String nome;
    private String classe;
    private String status;
    private boolean selecionado = false;


    // Construtor sem argumentos necessário para o DataFirebase
    public Aluno(){

    }

    public Aluno(String id, int matricula, String nome, String classe,
                 String status) {
        this.pessoaID = id;
        this.matricula = matricula;
        this.nome = nome;
        this.classe = classe;
        this.status = status;
    }


    public int getMatricula() {
        return matricula;
    }

    public String getNome() {
        return nome;
    }

    public String getClasse() {
        return classe;
    }

    public String getStatus() {
        return status;
    }

    public  String getPessoaID() { return pessoaID;}

    public boolean isSelecionado() {
        return selecionado;
    }

    public void setSelecionado(boolean selecionado) {
        this.selecionado = selecionado;
    }
}
