package com.example.ui.fragment

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.core.flow.FlowEntity
import com.example.core.flow.FlowEntityProvider
import com.example.core.flow.di.scope.FragmentScope
import com.example.ui.activity.BaseActivity
import com.example.ui.fragment.di.qualifier.FragmentViewModelFactory
import com.example.ui.mvi.presenter.Presenter
import com.example.ui.mvi.view.MviVewInjectable
import com.example.ui.mvi.view.MviView
import com.example.ui.mvi.view.MviViewImpl
import com.example.ui.mvi.view.ScreenConfiguration
import javax.inject.Inject
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

abstract class BaseFragment<Component : Any, State : Any, P :Presenter> :
    Fragment(),
    MviVewInjectable<Component>,
    MviView<Component, State, P> by MviViewImpl() {

    protected abstract val viewBinding: ViewBinding

    protected val activityMviView: BaseActivity<*, *, *> get() = activity as BaseActivity<*, *, *>

    @Inject
    @FragmentScope
    lateinit var presenter: P

    @Inject
    @FragmentScope
    @FragmentViewModelFactory
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected abstract fun provideViewModel(): ViewModel

    protected fun <T: ViewModel> initializeViewModel(clazz: Class<T>): ViewModel {
        return ViewModelProvider(this, viewModelFactory).get(clazz)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        onCreateMviView(::createComponent, ::inject)

        super.onViewCreated(view, savedInstanceState)

        initialize(
            viewModel = provideViewModel(),
            presenter = presenter
        )

        observeStateChange()
            .subscribeTillDestroy { (state, payload) -> render(state, payload) }
    }

    override fun onResume() {
        super.onResume()
        onAttachMviView()
    }

    override fun onPause() {
        super.onPause()
        onDetachMviView()
    }

    override fun onDestroy() {
        super.onDestroy()
        onDestroyMviView()
    }

    protected abstract fun render(state: State, payload: Any?)

    @Suppress("UNCHECKED_CAST")
    protected fun <T> getParentFragmentComponent(): T = activityMviView.component as T

    protected inline fun <reified T : ViewBinding> viewBindings(noinline initializer: (LayoutInflater) -> T): ReadOnlyProperty<Fragment, T> =
        ViewBindingProperty(initializer)

    protected class ViewBindingProperty<T : ViewBinding>(private val initializer: (LayoutInflater) -> T) :
        ReadOnlyProperty<Fragment, T> {
        private var value: T? = null

        override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
            return value ?: initializer(thisRef.layoutInflater).also { viewBinding -> value = viewBinding }
        }
    }

    protected inline fun <reified T : FlowEntity> AppCompatActivity.getFlowEntity(): T {
        return (application as FlowEntityProvider).getFlow(T::class.java)
    }

    protected fun <T : Parcelable> getConfiguration(): T? = arguments?.getParcelable(ScreenConfiguration.CONFIGURATION_PARCELABLE)

    protected fun <T : Parcelable> requireConfiguration(): T = requireNotNull(getConfiguration())
}