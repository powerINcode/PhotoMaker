package com.example.feature.browse.photo.impl.navigation

import androidx.appcompat.app.AppCompatActivity
import com.example.core.flow.navigation.FlowNavigator
import com.example.feature.browse.photo.impl.ui.BrowsePhotoActivity

object BrowsePhotoNavigator: FlowNavigator {
    override fun get(): Class<out AppCompatActivity> = BrowsePhotoActivity::class.java
}