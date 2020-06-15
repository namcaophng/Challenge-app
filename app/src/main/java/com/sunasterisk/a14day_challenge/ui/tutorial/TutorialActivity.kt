package com.sunasterisk.a14day_challenge.ui.tutorial

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.sunasterisk.a14day_challenge.BuildConfig
import com.sunasterisk.a14day_challenge.R
import com.sunasterisk.a14day_challenge.ui.ContextExtensions.Companion.showToast
import com.sunasterisk.a14day_challenge.ui.ExerciseActivity
import kotlinx.android.synthetic.main.activity_tutorial.*

class TutorialActivity : YouTubeBaseActivity(), View.OnClickListener,
    YouTubePlayer.OnInitializedListener, YouTubePlayer.OnFullscreenListener {

    private var typeExercise: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)

        initTypeExercise()
        registerListener()
        viewYoutube.initialize(BuildConfig.API_KEY, this)
        if (savedInstanceState != null) {
            viewYoutube.initialize(BuildConfig.API_KEY, this)
        }
    }

    private fun initTypeExercise() {
        typeExercise = intent.getStringExtra(ExerciseActivity.EXTRA_TYPE_EXERCISE) ?: return
        textTypeOfExercise.text = typeExercise
        textTutorial.text =
            when (typeExercise) {
                EXTRA_RUN -> getString(R.string.title_tutorial_run)
                EXTRA_PLANK -> getString(R.string.title_tutorial_plank)
                EXTRA_PUSH_UP -> getString(R.string.title_tutorial_push_up)
                else -> typeExercise
            }
    }

    private fun registerListener() {
        buttonDone.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonDone -> finish()
        }
    }

    override fun onInitializationSuccess(
        youtubeProvider: YouTubePlayer.Provider?,
        youTubePlayer: YouTubePlayer?,
        isReady: Boolean
    ) {
        showToast(R.string.title_success_load_video)
        if (!isReady) {
            val idVideo =
            when (typeExercise) {
                EXTRA_RUN -> ID_VIDEO_RUN
                EXTRA_PUSH_UP -> ID_VIDEO_PUSH_UP
                EXTRA_PLANK -> ID_VIDEO_PLANK
                else -> ID_VIDEO_DEFAULT
            }
            youTubePlayer?.cueVideo(idVideo)
        }
    }

    override fun onInitializationFailure(
        youtubeProvider: YouTubePlayer.Provider?,
        resultInintalization: YouTubeInitializationResult?
    ) {
        showToast(R.string.title_failure_load_video)
    }

    override fun onFullscreen(isFullScreen: Boolean) {
        if (isFullScreen) {
            showToast(R.string.title_full_screen)
        }
    }

    override fun onSaveInstanceState(bundle: Bundle) {
        bundle.putString(EXTRA_IS_PLAYING, EXTRA_PLAYING)
        super.onSaveInstanceState(bundle)
    }

    companion object {
        private const val EXTRA_RUN = "Run"
        private const val EXTRA_PUSH_UP = "Push up"
        private const val EXTRA_PLANK = "Plank"
        private const val ID_VIDEO_RUN = "gsUL3a1CxUQ"
        private const val ID_VIDEO_PUSH_UP = "IODxDxX7oi4"
        private const val ID_VIDEO_PLANK = "TvxNkmjdhMM"
        private const val ID_VIDEO_DEFAULT = "YjmQVMLhNT4"
        private const val EXTRA_IS_PLAYING = "isPlaying"
        private const val EXTRA_PLAYING = "playing"

        fun getIntent(context: Context, type: String) =
            Intent(context, TutorialActivity::class.java).apply {
                putExtra(ExerciseActivity.EXTRA_TYPE_EXERCISE, type)
            }
    }
}
