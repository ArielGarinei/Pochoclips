package com.example.user.pochoclips.models.POJO;

public class Filtro {
    private String nombreGeneroPocho;
    private Integer imagenGeneroPocho;
    private String idGenero;

    public Filtro(String nombreGeneroPocho, Integer imagenGeneroPocho, String idGenero) {
        this.nombreGeneroPocho = nombreGeneroPocho;
        this.imagenGeneroPocho = imagenGeneroPocho;
        this.idGenero = idGenero;
    }

    public String getNombreGeneroPocho() {
        return nombreGeneroPocho;
    }

    public Integer getImagenGeneroPocho() {
        return imagenGeneroPocho;
    }

    public String getIdGenero() {
        return idGenero;
    }
}
