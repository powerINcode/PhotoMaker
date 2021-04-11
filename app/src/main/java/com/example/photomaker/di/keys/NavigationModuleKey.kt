package com.example.photomaker.di.keys

import com.example.core.flow.FlowConfig
import dagger.MapKey
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class NavigationModuleKey(val value: KClass<out FlowConfig>)