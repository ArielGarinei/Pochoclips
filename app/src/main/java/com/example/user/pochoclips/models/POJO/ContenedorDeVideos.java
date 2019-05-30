package com.example.user.pochoclips.models.POJO;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aleja on 21/07/2018.
 */

public class ContenedorDeVideos {

    @SerializedName("results")
    private List<Video> videoList;

    public ContenedorDeVideos(List<Video> videoList) {
        this.videoList = videoList;
    }

    public List<Video> getVideoList() {
        return videoList;
    }
}
