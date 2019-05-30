package com.example.user.pochoclips.views.fragments;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.user.pochoclips.R;
import com.example.user.pochoclips.TMDBHelper;
import com.example.user.pochoclips.controllers.ControladorVideos;
import com.example.user.pochoclips.interfaces.ResultListener;
import com.example.user.pochoclips.models.POJO.Video;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentYoutube extends DialogFragment {


    public static final String KEY_YOUTUBE = "youtube" ;
    private Integer movieID;

    public FragmentYoutube() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_youtube, container, false);
        Bundle bundle= getArguments();
        movieID = bundle.getInt(KEY_YOUTUBE);
        cargarVideoYoutube();
        return view;
    }

    public void cargarVideoYoutube(){
        ControladorVideos controladorVideos = new ControladorVideos(getContext());
        controladorVideos.ObtenerVideos(new ResultListener<List<Video>>() {
            @Override
            public void finish(List<Video> resultado) {
                final String video = resultado.get(0).getKey();
                final YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
                youTubePlayerFragment.initialize(TMDBHelper.getApiKeyYoutube(), new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
                        player.loadVideo(video);
                    }
                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                    }
                });
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.add(R.id.youtube_fragment, youTubePlayerFragment).commit();
            }
        },movieID);
    }
}
