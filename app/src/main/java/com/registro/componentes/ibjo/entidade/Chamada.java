package com.registro.componentes.ibjo.entidade;

import java.util.List;

public class Chamada {

    private String Data;
    private String status;
    private List<Aluno> listPessoa;


    Chamada(){
    }


    public Chamada(String data, String status, List<Aluno> lista){
        this.Data = data;
        this.status = status;
        this.listPessoa = lista;
    }


    private void setData(String data) {
        Data = data;
    }

    private void setStatus(String status) {
        this.status = status;
    }

    private void setListPessoa(List<Aluno> listPessoa) {
        this.listPessoa = listPessoa;
    }
}
