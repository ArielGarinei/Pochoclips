package com.example.user.pochoclips.models.POJO;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by aleja on 09/07/2018.
 */
@Entity
public class People implements Serializable{
    @PrimaryKey
    @NonNull
    @SerializedName("imdb_id")
    @Expose
    private String imdb_id;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("birthday")
    @Expose
    private String birthday;

    @SerializedName("deathday")
    @Expose
    private String deathday;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("gender")
    @Expose
    private String gender;

    @SerializedName("biography")
    @Expose
    private String biography;

    @SerializedName("popularity")
    @Expose
    private String popularity;

    @SerializedName("place_of_birth")
    @Expose
    private String place_of_birth;

    @SerializedName("profile_path")
    @Expose
    private String profile_path;

    @SerializedName("adult")
    @Expose
    private String adult;

    @SerializedName("homepage")
    @Expose
    private String homepage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDeathday() {
        return deathday;
    }

    public void setDeathday(String deathday) {
        this.deathday = deathday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getPlace_of_birth() {
        return place_of_birth;
    }

    public void setPlace_of_birth(String place_of_birth) {
        this.place_of_birth = place_of_birth;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    @Override
    public String toString() {
        return "People{" +
                "imdb_id='" + imdb_id + '\'' +
                ", id='" + id + '\'' +
                ", birthday='" + birthday + '\'' +
                ", deathday='" + deathday + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", biography='" + biography + '\'' +
                ", popularity='" + popularity + '\'' +
                ", place_of_birth='" + place_of_birth + '\'' +
                ", profile_path='" + profile_path + '\'' +
                ", adult='" + adult + '\'' +
                ", homepage='" + homepage + '\'' +
                '}';
    }
}
