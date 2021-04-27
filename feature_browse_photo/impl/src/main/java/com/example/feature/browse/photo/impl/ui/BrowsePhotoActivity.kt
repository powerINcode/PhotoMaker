package com.example.feature.browse.photo.impl.ui

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import com.bumptech.glide.Glide
import com.example.feature.browse.photo.api.BrowsePhotoFlowConfig
import com.example.feature.browse.photo.impl.databinding.ActivityBrowsePhotoBinding
import com.example.feature.browse.photo.impl.di.BrowsePhotoActivityComponent
import com.example.feature.browse.photo.impl.di.DaggerBrowsePhotoActivityComponent
import com.example.feature.browse.photo.impl.ui.BrowsePhotoContract.BrowsePhotoState
import com.example.ui.activity.BaseActivity

class BrowsePhotoActivity : BaseActivity<BrowsePhotoActivityComponent, BrowsePhotoState, BrowsePhotoViewModel>() {

    override val viewBinding: ActivityBrowsePhotoBinding by viewBindings(ActivityBrowsePhotoBinding::inflate)

    override fun getViewModelClass(): Class<BrowsePhotoViewModel> = BrowsePhotoViewModel::class.java

    public override fun createComponent(): BrowsePhotoActivityComponent {
        return DaggerBrowsePhotoActivityComponent.factory().create(
            browsePhotoApi = getFlowEntity(),
            activity = this,
            photoId = intent.getLongExtra(BrowsePhotoFlowConfig.EXTRA_KEY_PHOTO_ID, -1).takeIf { it >= 0 }
                ?: throw IllegalStateException("Missing photo id")
        )
    }

    override fun inject(component: BrowsePhotoActivityComponent) {
        component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        hideSystemUI()

        viewBinding.fullScreenImageView.setOnClickListener {
            showSystemUI()
            Handler(mainLooper).postDelayed(::hideSystemUI, 3000)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun render(state: BrowsePhotoState, payload: Any?) {
        state.photo?.let { photo ->
            Glide.with(this)
                .load(photo.path)
                .into(viewBinding.fullScreenImageView)
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }

    private fun hideSystemUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            supportActionBar?.hide()
            window.insetsController?.let {
                // Default behavior is that if navigation bar is hidden, the system will "steal" touches
                // and show it again upon user's touch. We just want the user to be able to show the
                // navigation bar by swipe, touches are handled by custom code -> change system bar behavior.
                // Alternative to deprecated SYSTEM_UI_FLAG_IMMERSIVE.
                it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                // Finally, hide the system bars, alternative to View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                // and SYSTEM_UI_FLAG_FULLSCREEN.
                it.hide(WindowInsets.Type.systemBars())
            }
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
        }
    }

    private fun showSystemUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            supportActionBar?.show()
            window.setDecorFitsSystemWindows(false)
            // finally, show the system bars
            window.insetsController?.show(WindowInsets.Type.systemBars())
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        }
    }
}