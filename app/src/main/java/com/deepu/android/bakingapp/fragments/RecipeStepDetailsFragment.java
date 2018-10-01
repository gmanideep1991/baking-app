package com.deepu.android.bakingapp.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.deepu.android.bakingapp.R;
import com.deepu.android.bakingapp.models.RecipeStep;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepDetailsFragment extends Fragment {

    @BindView(R.id.recipe_step_description)
    TextView recipeStepDescription;
    @BindView(R.id.videoPlayer)
    PlayerView simpleExoPlayerView;
    @BindView(R.id.thumbnail)
    ImageView thumbnail;

    private SimpleExoPlayer player;

    private String description;
    private String videoUrl;
    private String imageUrl;

    private boolean videoExists;

    public RecipeStepDetailsFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_details,container,false);
        ButterKnife.bind(this,rootView);


        if(videoUrl!=null && !videoUrl.isEmpty()){
            thumbnail.setVisibility(View.GONE);
            videoExists = true;
            if(savedInstanceState==null){
                initializeExoPlayer(Uri.parse(videoUrl));
            }
            else{
                simpleExoPlayerView.setPlayer(player);
            }
        }
        else{
            if(imageUrl!=null && !imageUrl.isEmpty()){
                Glide.with(this).load(imageUrl).into(thumbnail);
            }else{
                thumbnail.setVisibility(View.GONE);
            }
            simpleExoPlayerView.setVisibility(View.GONE);
        }
        recipeStepDescription.setText(String.valueOf(description));
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            RecipeStep recipeStep = bundle.getParcelable(getString(R.string.recipe_step_detail));
            description = recipeStep.getDescription();
            videoUrl = recipeStep.getVideoURL();
            imageUrl = recipeStep.getThumbnailURL();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(videoExists){
            releasePlayer();
        }

    }

    private void initializeExoPlayer(Uri uri) {
        if(player == null){
            DefaultBandwidthMeter bandWidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandWidthMeter);

            DefaultTrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

            player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);

            simpleExoPlayerView.setPlayer(player);
            player.setPlayWhenReady(true);

            DataSource.Factory mediaDataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), "mediaPlayerSample"), (TransferListener<? super DataSource>) bandWidthMeter);
            MediaSource mediaSource = new ExtractorMediaSource.Factory(mediaDataSourceFactory)
                    .createMediaSource(uri);

            player.prepare(mediaSource);
        }
    }

    private void releasePlayer(){
        player.stop();
        player.release();
        player = null;
    }
}
