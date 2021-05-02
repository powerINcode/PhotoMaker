package com.example.feature.browse.photo.impl.ui.dummy

import androidx.lifecycle.ViewModel
import com.example.feature.browse.photo.impl.databinding.FragmentDummyBinding
import com.example.feature.browse.photo.impl.di.BrowsePhotoActivityComponent
import com.example.feature.browse.photo.impl.ui.dummy.di.DummyFragmentComponent
import com.example.ui.fragment.BaseFragment

internal class DummyFragment: BaseFragment<DummyFragmentComponent, DummyContract.DummyState, DummyPresenter>() {

    override val viewBinding: FragmentDummyBinding by viewBindings(FragmentDummyBinding::inflate)

    override fun provideViewModel(): ViewModel = initializeViewModel(DummyViewModel::class.java)

    override fun render(state: DummyContract.DummyState, payload: Any?) {
        viewBinding.phraseTextView.text = state.phrase
    }

    override fun createComponent(): DummyFragmentComponent {
        return getParentFragmentComponent<BrowsePhotoActivityComponent>().getDummyFragmentFactory()
            .create(this, requireConfiguration())
    }

    override fun inject(component: DummyFragmentComponent) {
        component.inject(this)
    }
}