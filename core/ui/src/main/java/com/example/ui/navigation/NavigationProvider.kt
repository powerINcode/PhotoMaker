package com.example.ui.navigation

import androidx.appcompat.app.AppCompatActivity
import com.example.core.flow.FlowConfig

/**
 * Provides [FlowConfig]
 */
interface NavigationProvider {
    /**
     * Provide [FlowConfig] by the class
     * @param config describe class of the requested [AppCompatActivity]
     */
    fun <T: FlowConfig> getNavigation(config: Class<T>): Class<AppCompatActivity>
}