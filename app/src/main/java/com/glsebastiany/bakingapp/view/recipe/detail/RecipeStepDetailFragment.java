package com.glsebastiany.bakingapp.view.recipe.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.glsebastiany.bakingapp.R;
import com.glsebastiany.bakingapp.databinding.FragmentRecipeStepDetailBinding;
import com.glsebastiany.bakingapp.repository.model.Step;
import com.glsebastiany.bakingapp.view.recipe.RecipeViewModel;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;


public class RecipeStepDetailFragment extends Fragment {

    private static final String ARG_STEP_INDEX = "step_index";

    private OnFragmentInteractionListener fragmentListener = null;

    private FragmentRecipeStepDetailBinding binding;

    private RecipeViewModel recipeViewModel;

    private int argStepIndex = 0;

    SimpleExoPlayer player = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            argStepIndex = getArguments().getInt(ARG_STEP_INDEX, 0);
        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_step_detail, container, false);

        recipeViewModel = ViewModelProviders.of(getActivity()).get(RecipeViewModel.class);

        binding.setFragment(this);

        recipeViewModel.getRecipe().observe(this, recipe -> {
            if (recipe != null && recipe.getSteps() != null) {
                binding.setObj(recipe.getSteps().get(argStepIndex));
                setupPlayer(recipe.getSteps().get(argStepIndex));
            }
        });

        return binding.getRoot();
    }

    private void setupPlayer(Step step) {
        if (step.getVideoURL() != null) {
            // Measures bandwidth during playback. Can be null if not required.
            DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            AdaptiveTrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
            // Create a default TrackSelector
            DefaultTrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
            DefaultLoadControl loadControls = new DefaultLoadControl();
            DefaultRenderersFactory renderersFactory = new DefaultRenderersFactory(getContext());
            // Create the player
            player = ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector, loadControls);
            // Attach
            binding.player.setPlayer(player);

            // Produces DataSource instances through which media data is loaded.
            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                    Util.getUserAgent(getContext(), getString(R.string.app_name)), bandwidthMeter);
            // Produces Extractor instances for parsing the media data.
            DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            // This is the MediaSource representing the media to be played.
            ExtractorMediaSource videoSource = new ExtractorMediaSource(Uri.parse(step.getVideoURL()),
                    dataSourceFactory, extractorsFactory, null, null);
            // Prepare the player with the source.
            player.prepare(videoSource);
            player.setPlayWhenReady(true);
        } else {
            binding.player.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        player.stop();
        player.release();
        player = null;
    }

    public void onNextClicked(View view) {
        if (fragmentListener != null)
            fragmentListener.onNextClick();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            fragmentListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentListener = null;
    }

    public RecipeViewModel getRecipeViewModel() {
        return recipeViewModel;
    }

    public interface OnFragmentInteractionListener {
        void onNextClick();
    }

    public static RecipeStepDetailFragment newInstance(int stepIndex) {
        RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_STEP_INDEX, stepIndex);
        fragment.setArguments(args);
        return fragment;
    }

}
