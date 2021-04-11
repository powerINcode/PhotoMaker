package com.example.ui.navigation

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import com.example.core.flow.FlowConfig

sealed class NavigationCommand

data class FeatureCommand<T : FlowConfig>(val config: T) : NavigationCommand()
data class ActivityCommand<T : AppCompatActivity>(val destination: Class<T>) : NavigationCommand()
data class CreatePhotoCommand(val requestCode: Int, val fileUri: Uri) : NavigationCommand()
object Finish : NavigationCommand()