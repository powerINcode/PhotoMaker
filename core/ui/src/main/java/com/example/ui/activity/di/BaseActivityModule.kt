package com.example.ui.activity.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.core.flow.di.scope.ActivityScope
import com.example.ui.navigation.Navigator
import com.example.ui.navigation.NavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Provider

@Module
interface BaseActivityModule {

    @ActivityScope
    @Binds
    fun provideNavigator(navigator: NavigatorImpl): Navigator

    companion object {
        @Suppress("UNCHECKED_CAST")
        @ActivityScope
        @Provides
        fun provideViewModelFactory(creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>) : ViewModelProvider.Factory = object :
            ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                var creator: Provider<out ViewModel>? = creators[modelClass]

                if (creator == null) {
                    for ((key, value) in creators) {
                        if (modelClass.isAssignableFrom(key)) {
                            creator = value
                            break
                        }
                    }
                }

                if (creator == null) {
                    throw IllegalArgumentException("unknown model class $modelClass")
                }

                try {
                    return creator.get() as T
                } catch (e: Exception) {
                    throw RuntimeException(e)
                }
            }
        }
    }
}