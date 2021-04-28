package com.example.ui.activity

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.core.flow.FlowEntity
import com.example.core.flow.FlowEntityProvider
import com.example.core.sreams.toMainThread
import com.example.ui.navigation.Navigator
import com.example.ui.viewmodel.BaseViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

abstract class BaseActivity<Component : Any, State : Any, VM : BaseViewModel<State, *>> : AppCompatActivity() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    protected abstract val viewBinding: ViewBinding

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory

    protected val viewModel: VM by lazy { ViewModelProvider(this, modelFactory).get(getViewModelClass()) }

    lateinit var component: Component

    protected abstract fun getViewModelClass(): Class<VM>
    protected abstract fun createComponent(): Component
    protected abstract fun inject(component: Component)

    override fun onCreate(savedInstanceState: Bundle?) {
        component = createComponent()
        inject(component)

        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        viewModel.navigation
            .toMainThread()
            .subscribeTillAttach {
                navigator.navigate(it)
            }

        viewModel.state
            .toMainThread()
            .subscribeTillAttach { stateChange ->
                render(stateChange.state, stateChange.payload)
            }

        viewModel.init()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
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

    protected fun <T: Parcelable> getConfiguration(): T? = intent.getParcelableExtra(ScreenConfiguration.CONFIGURATION_PARCELABLE)

    protected fun <T: Parcelable> requireConfiguration(): T = requireNotNull(getConfiguration())

    protected fun <T> Observable<T>.subscribeTillAttach(block: (T) -> Unit) {
        compositeDisposable.add(this.subscribe(block, {
            Log.e("BaseActivity", "Error happen in the subscribeTillDestroy: ${it.message}")
        }, {}))
    }
}