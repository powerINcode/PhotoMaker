package com.example.ui.navigation

import androidx.appcompat.app.AppCompatActivity
import com.example.core.flow.FlowConfig

interface NavigationProvider {
    fun <T: FlowConfig> getNavigation(config: Class<T>): Class<AppCompatActivity>
}