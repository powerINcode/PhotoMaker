package com.example.feature.browse.photo.impl.ui.dummy.di

import androidx.fragment.app.Fragment
import com.example.core.flow.di.FragmentComponent
import com.example.core.flow.di.scope.FragmentScope
import com.example.feature.browse.photo.impl.ui.dummy.DummyContract
import com.example.feature.browse.photo.impl.ui.dummy.DummyFragment
import com.example.ui.fragment.di.BaseFragmentModule
import dagger.BindsInstance
import dagger.Subcomponent

@FragmentScope
@Subcomponent(
    modules = [
        BaseFragmentModule::class,
        DummyFragmentModule::class
    ]
)
internal interface DummyFragmentComponent : FragmentComponent<DummyFragment> {
    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance fragment: Fragment,
            @BindsInstance configuration: DummyContract.Configuration
        ): DummyFragmentComponent
    }
}