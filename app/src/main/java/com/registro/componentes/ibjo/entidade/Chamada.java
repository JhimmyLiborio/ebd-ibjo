package com.registro.componentes.ibjo.entidade;

import java.util.List;

public class Chamada {

    //private String data;
    private String idUser;
    private boolean presente;

    // Construtor sem argumentos necessário para o DataFirebase
    public Chamada(){

    }


    public Chamada(String keyUser, boolean presenca){
       // this.data = data;
        this.idUser = keyUser;
        this.presente = presenca;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public boolean isPresente() {
        return presente;
    }

    public void setPresente(boolean presente) {
        this.presente = presente;
    }

   /* public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }*/
}
