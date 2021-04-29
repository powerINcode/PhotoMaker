package com.example.ui.fragment.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.core.flow.di.scope.FragmentScope
import com.example.ui.fragment.di.qualifier.FragmentViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Provider

@Module
interface BaseFragmentModule {

    companion object {
        @Suppress("UNCHECKED_CAST")
        @FragmentScope
        @FragmentViewModelFactory
        @Provides
        fun provideFragmentViewModelFactory(creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>) : ViewModelProvider.Factory = object :
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