package com.example.photomaker.di.keys

import com.example.core.flow.FlowEntity
import dagger.MapKey
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class FlowModuleKey(val value: KClass<out FlowEntity>)