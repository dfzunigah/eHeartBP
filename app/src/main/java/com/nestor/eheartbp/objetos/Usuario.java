package com.nestor.eheartbp.objetos;

public class Usuario {
    int Diastolica;
    int Sistolica;
    int Pulso;

    public Usuario() {
    }

    public Usuario(int diastolica, int sistolica, int pulso) {
        Diastolica = diastolica;
        Sistolica = sistolica;
        Pulso = pulso;
    }

    public int getDiastolica() {
        return Diastolica;
    }

    public void setDiastolica(int diastolica) {
        Diastolica = diastolica;
    }

    public int getSistolica() {
        return Sistolica;
    }

    public void setSistolica(int sistolica) {
        Sistolica = sistolica;
    }

    public int getPulso() {
        return Pulso;
    }

    public void setPulso(int pulso) {
        Pulso = pulso;
    }
}
