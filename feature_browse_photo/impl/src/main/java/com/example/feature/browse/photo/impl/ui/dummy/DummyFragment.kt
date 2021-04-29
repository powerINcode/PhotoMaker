package com.example.feature.browse.photo.impl.ui.dummy

import com.example.feature.browse.photo.impl.databinding.FragmentDummyBinding
import com.example.feature.browse.photo.impl.di.BrowsePhotoActivityComponent
import com.example.feature.browse.photo.impl.ui.dummy.di.DummyFragmentComponent
import com.example.ui.fragment.BaseFragment

internal class DummyFragment: BaseFragment<DummyFragmentComponent, DummyContract.DummyState, DummyViewModel>() {

    override val viewBinding: FragmentDummyBinding by viewBindings(FragmentDummyBinding::inflate)

    override fun render(state: DummyContract.DummyState, payload: Any?) {
        viewBinding.phraseTextView.text = state.phrase
    }

    override fun getViewModelClass(): Class<DummyViewModel> = DummyViewModel::class.java

    override fun createComponent(): DummyFragmentComponent {
        return getParentFragmentComponent<BrowsePhotoActivityComponent>().getDummyFragmentFactory()
            .create(this, requireConfiguration())
    }

    override fun inject(component: DummyFragmentComponent) {
        component.inject(this)
    }
}