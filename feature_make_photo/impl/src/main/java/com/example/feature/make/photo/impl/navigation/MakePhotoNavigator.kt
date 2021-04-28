package com.example.feature.make.photo.impl.navigation

import androidx.appcompat.app.AppCompatActivity
import com.example.core.flow.navigation.FlowNavigator
import com.example.feature.make.photo.impl.ui.MakePhotoActivity

object MakePhotoNavigator: FlowNavigator {
    override fun get(): Class<out AppCompatActivity> = MakePhotoActivity::class.java
}