package com.example.feature.make.photo.impl.ui

import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.feature.make.photo.impl.databinding.ActivityMakePhotoBinding
import com.example.feature.make.photo.impl.di.DaggerMakePhotoActivityComponent
import com.example.feature.make.photo.impl.di.MakePhotoActivityComponent
import com.example.feature.make.photo.impl.ui.MakePhotoContract.MakePhotoIntent
import com.example.feature.make.photo.impl.ui.MakePhotoContract.MakePhotoState
import com.example.ui.activity.BaseActivity
import com.example.ui.textview.setText

class MakePhotoActivity : BaseActivity<MakePhotoActivityComponent, MakePhotoState, MakePhotoViewModel>() {

    override val viewBinding: ActivityMakePhotoBinding by viewBindings(ActivityMakePhotoBinding::inflate)

    override fun getViewModelClass(): Class<MakePhotoViewModel> = MakePhotoViewModel::class.java

    private val glide by lazy(LazyThreadSafetyMode.NONE) {
        Glide.with(this)
    }

    public override fun createComponent(): MakePhotoActivityComponent {
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
            viewModel.send(MakePhotoIntent.PhotoMade)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(viewBinding) {
            createPhotoButton.setOnClickListener { viewModel.send(MakePhotoIntent.MakePhoto) }
            savePhotoButton.setOnClickListener { viewModel.send(MakePhotoIntent.SavePhoto(name = photoNameEditText.text.toString())) }
        }
    }

    override fun render(state: MakePhotoState) {
        with(viewBinding) {
            state.photoUri?.let { glide.load(it).into(photoImageView) }
            progressBar.isVisible = state.loading
            errorTextView.setText(state.error)
            errorTextView.isVisible = state.error != null
        }
    }

    override fun onBackPressed() {
        viewModel.send(MakePhotoIntent.Back)
    }
}