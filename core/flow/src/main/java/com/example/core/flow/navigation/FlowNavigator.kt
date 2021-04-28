package com.example.core.flow.navigation

import androidx.appcompat.app.AppCompatActivity

interface FlowNavigator {
    fun get(): Class<out AppCompatActivity>
}