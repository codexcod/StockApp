package com.example.sgim_stock;

public class Item {

    private String nombre;
    private Integer src;



    public Item(String nombre, Integer src) {
        this.nombre = nombre;
        this.src = src;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getSrc() {
        return src;
    }

    public void setSrc(Integer src) {
        this.src = src;
    }
}
