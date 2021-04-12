package com.example.ui.navigation

/**
 * Provide navigation
 */
interface Navigator {
    /**
     * Navigate to particular [NavigationCommand]
     * @param command [NavigationCommand]
     */
    fun navigate(command: NavigationCommand)
}
