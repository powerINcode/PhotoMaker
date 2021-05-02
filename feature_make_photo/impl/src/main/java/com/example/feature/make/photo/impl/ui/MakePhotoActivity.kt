package com.example.feature.make.photo.impl.ui

import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.example.feature.make.photo.impl.databinding.ActivityMakePhotoBinding
import com.example.feature.make.photo.impl.di.DaggerMakePhotoActivityComponent
import com.example.feature.make.photo.impl.di.MakePhotoActivityComponent
import com.example.feature.make.photo.impl.ui.MakePhotoContract.MakePhotoIntent
import com.example.feature.make.photo.impl.ui.MakePhotoContract.MakePhotoState
import com.example.ui.activity.BaseActivity
import com.example.ui.textview.setText

internal class MakePhotoActivity : BaseActivity<MakePhotoActivityComponent, MakePhotoState, MakePhotoPresenter>() {

    override val viewBinding: ActivityMakePhotoBinding by viewBindings(ActivityMakePhotoBinding::inflate)

    private val glide by lazy(LazyThreadSafetyMode.NONE) {
        Glide.with(this)
    }

    override fun provideViewModel(): ViewModel = initializeViewModel(MakePhotoViewModel::class.java)

    override fun createComponent(): MakePhotoActivityComponent {
        return DaggerMakePhotoActivityComponent.factory().create(
            activity = this,
            makePhotoApi = getFlowEntity()
        )
    }

    override fun inject(component: MakePhotoActivityComponent) {
        component.inject(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == MakePhotoContract.REQUEST_CODE_MAKE_PHOTO && resultCode == RESULT_OK) {
            presenter.send(MakePhotoIntent.PhotoMade)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(viewBinding) {
            createPhotoButton.setOnClickListener { presenter.send(MakePhotoIntent.MakePhoto) }
            savePhotoButton.setOnClickListener { savePhoto() }
        }
    }

    override fun render(state: MakePhotoState, payload: Any?) {
        with(viewBinding) {
            state.photoUri?.let { glide.load(it).into(photoImageView) }
            progressBar.isVisible = state.loading
            errorTextView.setText(state.error)
            errorTextView.isVisible = state.error != null
        }
    }

    override fun onBackPressed() {
        presenter.send(MakePhotoIntent.Back)
    }

    private fun savePhoto() {
        presenter.send(MakePhotoIntent.SavePhoto(name = viewBinding.photoNameEditText.text.toString()))
    }
}