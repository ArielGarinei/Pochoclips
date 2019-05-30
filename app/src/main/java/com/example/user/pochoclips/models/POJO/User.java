package com.example.user.pochoclips.models.POJO;

/**
 * Created by Arielo on 21/7/2018.
 */

public class User {
    private String uId;
    private String email;
    private String name;
    private Integer puntajeRecomendador;
    private String  photoPorfile;

    public User() {
    }

    public User(String uId, String email, String name, Integer puntajeRecomendador, String photoPorfile) {
        this.uId = uId;
        this.email = email;
        this.name = name;
        this.puntajeRecomendador = puntajeRecomendador;
        this.photoPorfile = photoPorfile;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPuntajeRecomendador() {
        return puntajeRecomendador;
    }

    public void setPuntajeRecomendador(Integer puntajeRecomendador) {
        this.puntajeRecomendador = puntajeRecomendador;
    }

    public String getPhotoPorfile() {
        return photoPorfile;
    }

    public void setPhotoPorfile(String photoPorfile) {
        this.photoPorfile = photoPorfile;
    }
}
