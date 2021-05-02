package com.example.ui.activity

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.core.flow.FlowEntity
import com.example.core.flow.FlowEntityProvider
import com.example.core.flow.di.scope.ActivityScope
import com.example.ui.mvi.presenter.Presenter
import com.example.ui.mvi.view.MviVewInjectable
import com.example.ui.mvi.view.MviView
import com.example.ui.mvi.view.MviViewImpl
import com.example.ui.mvi.view.ScreenConfiguration
import javax.inject.Inject
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

abstract class BaseActivity<Component : Any, State : Any, P: Presenter> :
    AppCompatActivity(),
    MviVewInjectable<Component>,
    MviView<Component, State, P> by MviViewImpl() {

    protected abstract val viewBinding: ViewBinding

    @Inject
    @ActivityScope
    lateinit var presenter: P

    @Inject
    @ActivityScope
    lateinit var viewModelFactory: ViewModelProvider.Factory



    override fun onCreate(savedInstanceState: Bundle?) {
        onCreateMviView(::createComponent, ::inject)

        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

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

    protected abstract fun provideViewModel(): ViewModel

    protected fun <T: ViewModel> initializeViewModel(clazz: Class<T>): ViewModel {
        return ViewModelProvider(this, viewModelFactory).get(clazz)
    }

    protected abstract fun render(state: State, payload: Any?)

    protected inline fun <reified T : ViewBinding> viewBindings(noinline initializer: (LayoutInflater) -> T): ReadOnlyProperty<AppCompatActivity, T> =
        ViewBindingProperty(initializer)

    protected class ViewBindingProperty<T : ViewBinding>(private val initializer: (LayoutInflater) -> T) :
        ReadOnlyProperty<AppCompatActivity, T> {
        private var value: T? = null

        override fun getValue(thisRef: AppCompatActivity, property: KProperty<*>): T {
            return value ?: initializer(thisRef.layoutInflater).also { viewBinding -> value = viewBinding }
        }
    }

    protected inline fun <reified T : FlowEntity> AppCompatActivity.getFlowEntity(): T {
        return (application as FlowEntityProvider).getFlow(T::class.java)
    }

    protected fun <T : Parcelable> getConfiguration(): T? = intent.getParcelableExtra(ScreenConfiguration.CONFIGURATION_PARCELABLE)

    protected fun <T : Parcelable> requireConfiguration(): T = requireNotNull(getConfiguration())
}