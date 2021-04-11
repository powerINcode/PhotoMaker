package com.example.feature.photo.gallery.impl.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.example.feature.photo.gallery.impl.R
import com.example.feature.photo.gallery.impl.databinding.ItemPhotoBinding
import com.example.feature.photo.gallery.impl.ui.adapter.PhotoGalleryAdapter.Model
import com.example.feature.photo.gallery.impl.ui.adapter.PhotoGalleryAdapter.ViewHolder
import com.example.ui.recyclerview.BaseRecyclerView

class PhotoGalleryAdapter: BaseRecyclerView<Model, ViewHolder>() {

    var itemSize: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemSize, items[position])
    }

    class ViewHolder(view: View): BaseRecyclerView.BaseViewHolder<Model>(view) {
        private val viewBinding by viewBindings(ItemPhotoBinding::bind)
        private val photoImageView: ImageView = viewBinding.itemPhotoImageView
        private val nameTextView: TextView = viewBinding.nameTextView
        private val dateTextView: TextView = viewBinding.dateTextView

        fun bind(size: Int, item: Model) {
            super.bind(item)

            itemView.layoutParams = ConstraintLayout.LayoutParams(size, size)
            Glide.with(context)
                .load(item.path)
                .centerCrop()
                .into(photoImageView)

            nameTextView.text = item.name
            dateTextView.text = item.date
        }
    }

    fun itemSizeChange(size: Int) {
        itemSize = size
        notifyDataSetChanged()
    }

    data class Model(
        override val id: String,
        val name: String,
        val date: String,
        val path: String
        ): BaseRecyclerView.Model
}