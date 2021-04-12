package com.example.ui.navigation

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.core.flow.FlowConfig

/**
 * Entity describe navigation intent
 */
sealed class NavigationCommand

/**
 * Navigate to the feature
 * @param config describe [FlowConfig] attached to the feature
 */
data class FeatureCommand<T : FlowConfig>(val config: T, val extra: Bundle? = null) : NavigationCommand()

/**
 * Navigate to the activity
 */
data class ActivityCommand<T : AppCompatActivity>(val destination: Class<T>, val extra: Bundle? = null) : NavigationCommand()

/**
 * Navigate to the camera application
 * @param requestCode requesting code
 * @param fileUri uri of the file for the new created photo
 */
data class CreatePhotoCommand(val requestCode: Int, val fileUri: Uri) : NavigationCommand()

/**
 * Close activity
 */
object Finish : NavigationCommand()