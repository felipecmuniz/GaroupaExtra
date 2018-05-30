package com.example.felipe_pc.garoupaextra.model;

/**
 * A broadcast receiver who listens for incoming SMS
 */

public class Gasto {

    private double valor;
    private String data;
    private String hora;
    private String local;
    private String tipo;
    private String banco_nome;
    private String banco_det;
    private Categoria categoria;
    private String notas;
    private String tags;

   public Gasto(){
       this.valor = valor;
       this.data = data;
       this.local = local;
       this.hora = "";
       this.tipo = "";
       this.banco_nome = "";
       this.banco_det = "";
       this.categoria = new Categoria();
       this.notas = "";
       this.tags = "";
   }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getBanco_nome() {
        return banco_nome;
    }

    public void setBanco_nome(String banco_nome) {
        this.banco_nome = banco_nome;
    }

    public String getBanco_det() {
        return banco_det;
    }

    public void setBanco_det(String banco_det) {
        this.banco_det = banco_det;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
