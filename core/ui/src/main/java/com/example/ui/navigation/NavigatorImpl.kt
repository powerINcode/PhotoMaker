package com.example.ui.navigation

import android.annotation.SuppressLint
import android.content.Intent
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import javax.inject.Inject

class NavigatorImpl @Inject constructor(
    private val activity: AppCompatActivity
) : Navigator {

    @SuppressLint("QueryPermissionsNeeded")
    override fun navigate(command: NavigationCommand) {
        when (command) {
            is Finish -> activity.finish()
            is ActivityCommand<*> -> activity.startActivity(Intent(activity, command.destination))
            is CreatePhotoCommand -> {
                Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                    takePictureIntent.resolveActivity(activity.packageManager)?.apply {
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, command.fileUri)
                        activity.startActivityForResult(takePictureIntent, command.requestCode)
                    }
                }
            }
            is FeatureCommand<*> -> activity.startActivity(
                Intent(
                    activity,
                    (activity.application as NavigationProvider).getNavigation(command.config::class.java)
                )
            )
        }
    }

}