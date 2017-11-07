package com.glsebastiany.bakingapp.view.recipe.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.glsebastiany.bakingapp.R
import com.glsebastiany.bakingapp.databinding.FragmentRecipeStepDetailBinding
import com.glsebastiany.bakingapp.repository.model.Step
import com.glsebastiany.bakingapp.view.recipe.RecipeViewModel
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util


class RecipeStepDetailFragment : Fragment() {

    private var fragmentListener: OnFragmentInteractionListener? = null

    private lateinit var binding: FragmentRecipeStepDetailBinding

    lateinit var recipeViewModel: RecipeViewModel
        private set

    private var argStepIndex: Int = 0

    var player: SimpleExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let{
            argStepIndex = it.getInt(ARG_STEP_INDEX, 0)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_step_detail, container, false)

        recipeViewModel = ViewModelProviders.of(activity!!).get(RecipeViewModel::class.java)

        binding.fragment = this

        recipeViewModel.getRecipe().observe(this, Observer { recipe ->
            recipe?.steps?.let { steps ->
                binding.obj = steps[argStepIndex]
                setupPlayer(steps[argStepIndex])
            }
        })


        return binding.root
    }

    private fun setupPlayer(step: Step) {
        if (step.videoURL != null) {
            // Measures bandwidth during playback. Can be null if not required.
            val bandwidthMeter = DefaultBandwidthMeter()
            val videoTrackSelectionFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)
            // Create a default TrackSelector
            val trackSelector = DefaultTrackSelector(videoTrackSelectionFactory)
            val loadControls = DefaultLoadControl()
            val renderersFactory = DefaultRenderersFactory(context)
            // Create the player
            player = ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector, loadControls)
            // Attach
            binding.player.player = player

            // Produces DataSource instances through which media data is loaded.
            val dataSourceFactory = DefaultDataSourceFactory(context,
                    Util.getUserAgent(context, getString(R.string.app_name)), bandwidthMeter)
            // Produces Extractor instances for parsing the media data.
            val extractorsFactory = DefaultExtractorsFactory()
            // This is the MediaSource representing the media to be played.
            val videoSource = ExtractorMediaSource(Uri.parse(step.videoURL),
                    dataSourceFactory, extractorsFactory, null, null)
            // Prepare the player with the source.
            player?.prepare(videoSource)
            player?.playWhenReady = true
        } else {
            binding.player.visibility = View.GONE
        }
    }

    override fun onStop() {
        super.onStop()
        player?.stop()
        player?.release()
        player = null
    }

    fun onNextClicked(view: View) {
        fragmentListener?.onNextClick()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            fragmentListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        fragmentListener = null
    }

    interface OnFragmentInteractionListener {
        fun onNextClick()
    }

    companion object {

        private val ARG_STEP_INDEX = "step_index"

        fun newInstance(stepIndex: Int): RecipeStepDetailFragment {
            val fragment = RecipeStepDetailFragment()
            val args = Bundle()
            args.putInt(ARG_STEP_INDEX, stepIndex)
            fragment.arguments = args
            return fragment
        }
    }

}
