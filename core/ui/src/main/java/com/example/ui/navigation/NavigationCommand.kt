package com.example.ui.navigation

import android.net.Uri
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.core.flow.FlowConfig

/**
 * Entity describe navigation intent
 */
sealed class NavigationCommand

/**
 * Navigate to the feature
 * @param config describe [FlowConfig] attached to the feature
 */
data class FeatureCommand<T : FlowConfig>(val config: T) : NavigationCommand()

/**
 * Navigate to the activity
 */
data class ActivityCommand<T : AppCompatActivity>(
    val destination: Class<T>,
    val configuration: Parcelable? = null,
    val flags: List<Int> = emptyList(),
    val requestCode: Int? = null) : NavigationCommand()

/**
 * Navigate to the fragment
 */
data class FragmentCommand<T: Fragment>(
    val destination: T,
    val configuration: Parcelable? = null,
    val addToBackStack: Boolean = true,
    val backStackPolicy: BackStackPolicy = BackStackPolicy.Add
): NavigationCommand() {
    sealed class BackStackPolicy {
        object Add: BackStackPolicy()
        object Replace: BackStackPolicy()
    }
}

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

/**
 * Dummy command
 */
object DummyCommand: NavigationCommand()

/**
 * Commands container
 */
data class CommandSequence(val commands: List<NavigationCommand>): NavigationCommand()

operator fun NavigationCommand.plus(command: NavigationCommand): NavigationCommand {
    return when (this) {
        is CommandSequence -> if (command is CommandSequence) {
            CommandSequence(this.commands + command.commands)
        } else {
            CommandSequence(this.commands + listOf(command))
        }
        else -> if (command is CommandSequence) {
            CommandSequence(listOf(this) + command.commands)
        } else {
            CommandSequence(listOf(this, command))
        }
    }
}