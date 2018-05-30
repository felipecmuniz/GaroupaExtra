package com.example.felipe_pc.garoupaextra.model;

import com.example.felipe_pc.garoupaextra.R;

/**
 * A broadcast receiver who listens for incoming SMS
 */

public class Categoria {

    private String nome_cat;
    private String desc_cat;
    private int img_cat;
    private int id_cat;


   public Categoria(){
       this.nome_cat = "Outros";
       this.desc_cat = "Descrição da categoria Outros";
       this.img_cat = R.drawable.cat_icon_outros;

   }

    public String getNome_cat() {
        return nome_cat;
    }

    public void setNome_cat(String nome_cat) {
        this.nome_cat = nome_cat;
    }

    public String getDesc_cat() {
        return desc_cat;
    }

    public void setDesc_cat(String desc_cat) {
        this.desc_cat = desc_cat;
    }

    public int getImg_cat() {
        return img_cat;
    }

    public void setImg_cat(int img_cat) {
        this.img_cat = img_cat;
    }

    public int getId_cat() {
        return id_cat;
    }

    public void setId_cat(int id_cat) {
        this.id_cat = id_cat;
    }
}
