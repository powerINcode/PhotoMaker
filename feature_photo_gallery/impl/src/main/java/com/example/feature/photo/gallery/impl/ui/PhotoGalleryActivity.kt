package com.example.feature.photo.gallery.impl.ui

import android.os.Bundle
import androidx.core.view.doOnLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.example.feature.photo.gallery.impl.R
import com.example.feature.photo.gallery.impl.databinding.ActivityPhotoGalleryBinding
import com.example.feature.photo.gallery.impl.di.DaggerPhotoGalleryActivityComponent
import com.example.feature.photo.gallery.impl.di.PhotoGalleryActivityComponent
import com.example.feature.photo.gallery.impl.ui.PhotoGalleryContract.*
import com.example.feature.photo.gallery.impl.ui.adapter.PhotoGalleryAdapter
import com.example.ui.activity.BaseActivity

class PhotoGalleryActivity : BaseActivity<PhotoGalleryActivityComponent, PhotoGalleryState, PhotoGalleryViewModel>() {

    override val viewBinding: ActivityPhotoGalleryBinding by viewBindings(ActivityPhotoGalleryBinding::inflate)

    override fun getViewModelClass(): Class<PhotoGalleryViewModel> = PhotoGalleryViewModel::class.java

    private val galleryAdapter = PhotoGalleryAdapter()

    public override fun createComponent(): PhotoGalleryActivityComponent {
        return DaggerPhotoGalleryActivityComponent.factory().create(
            activity = this,
            photoGalleryApi = getFlowEntity()
        )
    }

    override fun inject(component: PhotoGalleryActivityComponent) {
        component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding.photoGalleryRecyclerView.doOnLayout {
            val intent = PhotoGalleryIntent.ContainerSizeChange(
                containerSize = it.width,
                defaultItemSize = resources.getDimensionPixelSize(R.dimen.photo_size)
            )

            viewModel.send(intent)
        }

        with(viewBinding) {
            photoGalleryRecyclerView.apply {
                setHasFixedSize(true)
                adapter = galleryAdapter
            }

            makeNewPhotoButton.setOnClickListener { viewModel.send(PhotoGalleryIntent.MakePhoto) }
        }

        galleryAdapter.onPhotoClick = { model ->
            viewModel.send(PhotoGalleryIntent.PhotoClick(model.photoId))
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