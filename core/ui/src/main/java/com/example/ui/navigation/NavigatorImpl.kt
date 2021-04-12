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
            is ActivityCommand<*> -> {
                val intent = Intent(activity, command.destination)
                if (command.extra != null) {
                    intent.putExtras(command.extra)
                }
                activity.startActivity(intent)
            }
            is CreatePhotoCommand -> {
                Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                    takePictureIntent.resolveActivity(activity.packageManager)?.apply {
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, command.fileUri)
                        activity.startActivityForResult(takePictureIntent, command.requestCode)
                    }
                }
            }
            is FeatureCommand<*> -> {
                val requestedActivityClass = (activity.application as NavigationProvider).getNavigation(command.config::class.java)
                navigate(ActivityCommand(requestedActivityClass, extra = command.extra))
            }
        }
    }

}