package com.example.ui.navigation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.example.ui.R
import com.example.ui.mvi.view.ScreenConfiguration
import javax.inject.Inject

class NavigatorImpl @Inject constructor(
    private val activity: AppCompatActivity
) : Navigator {

    @SuppressLint("QueryPermissionsNeeded")
    override fun navigate(command: NavigationCommand) {
        when (command) {
            is DummyCommand -> Unit
            is CommandSequence -> command.commands.forEach { navigate(it) }
            is Finish -> activity.finish()
            is ActivityCommand<*> -> {
                val intent = Intent(activity, command.destination)
                if (command.configuration != null) {
                    intent.putExtra(ScreenConfiguration.CONFIGURATION_PARCELABLE, command.configuration)
                }
                command.flags.forEach { flag -> intent.addFlags(flag) }
                command.requestCode?.let { activity.startActivityForResult(intent, it) } ?: activity.startActivity(intent)
            }
            is FragmentCommand<*> -> {
                val fragment = command.destination.apply {
                    arguments = Bundle().apply {
                        putParcelable(ScreenConfiguration.CONFIGURATION_PARCELABLE, command.configuration)
                    }
                }
                activity.supportFragmentManager.beginTransaction().let { transaction ->
                    val transactionArgs = Triple(R.id.fragmentContainerId, fragment, null)
                    when (command.backStackPolicy) {
                        FragmentCommand.BackStackPolicy.Add -> transaction.add(transactionArgs.first, transactionArgs.second, transactionArgs.third)
                        FragmentCommand.BackStackPolicy.Replace -> transaction.replace(transactionArgs.first, transactionArgs.second, transactionArgs.third)
                    }.run {
                        if (command.addToBackStack) {
                            transaction.addToBackStack(null)
                        } else {
                            transaction
                        }
                    }
                }.commit()
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
                navigate(ActivityCommand(requestedActivityClass, configuration = command.config))
            }
        }
    }

}