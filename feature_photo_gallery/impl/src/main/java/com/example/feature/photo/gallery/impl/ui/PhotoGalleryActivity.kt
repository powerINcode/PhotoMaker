package com.example.feature.photo.gallery.impl.ui

import android.os.Bundle
import androidx.core.view.doOnLayout
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import com.example.feature.photo.gallery.impl.R
import com.example.feature.photo.gallery.impl.databinding.ActivityPhotoGalleryBinding
import com.example.feature.photo.gallery.impl.di.DaggerPhotoGalleryActivityComponent
import com.example.feature.photo.gallery.impl.di.PhotoGalleryActivityComponent
import com.example.feature.photo.gallery.impl.ui.PhotoGalleryContract.*
import com.example.feature.photo.gallery.impl.ui.adapter.PhotoGalleryAdapter
import com.example.ui.activity.BaseActivity

internal class PhotoGalleryActivity : BaseActivity<PhotoGalleryActivityComponent, PhotoGalleryState, PhotoGalleryPresenter>() {

    override val viewBinding: ActivityPhotoGalleryBinding by viewBindings(ActivityPhotoGalleryBinding::inflate)


    private val galleryAdapter = PhotoGalleryAdapter()

    override fun createComponent(): PhotoGalleryActivityComponent {
        return DaggerPhotoGalleryActivityComponent.factory().create(
            activity = this,
            photoGalleryApi = getFlowEntity()
        )
    }

    override fun inject(component: PhotoGalleryActivityComponent) {
        component.inject(this)
    }

    override fun provideViewModel(): ViewModel = initializeViewModel(PhotoGalleryViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding.photoGalleryRecyclerView.doOnLayout {
            val intent = PhotoGalleryIntent.ContainerSizeChange(
                containerSize = it.width,
                defaultItemSize = resources.getDimensionPixelSize(R.dimen.photo_size)
            )

            presenter.send(intent)
        }

        with(viewBinding) {
            photoGalleryRecyclerView.apply {
                setHasFixedSize(true)
                adapter = galleryAdapter
            }

            makeNewPhotoButton.setOnClickListener { presenter.send(PhotoGalleryIntent.MakePhoto) }
        }

        galleryAdapter.onPhotoClick = { model ->
            presenter.send(PhotoGalleryIntent.PhotoClick(model.photoId))
        }
    }

    override fun render(state: PhotoGalleryState, payload: Any?) {
        val statePayload = state.get(payload)

        with(viewBinding) {
            if (statePayload.spanCountChanged) {
                photoGalleryRecyclerView.layoutManager = GridLayoutManager(this@PhotoGalleryActivity, state.spanCount)
            }

            if (statePayload.photoSizeChanged) {
                galleryAdapter.itemSizeChange(state.photoSize)
            }

            if (statePayload.photosChanged) {
                galleryAdapter.swap(state.photos)
            }
        }


    }

}