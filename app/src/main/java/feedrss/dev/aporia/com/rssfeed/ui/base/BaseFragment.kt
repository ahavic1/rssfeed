package feedrss.dev.aporia.com.rssfeed.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment

abstract class BaseFragment<ViewModelType : BaseViewModel> : BaseView<ViewModelType>, Fragment() {

    protected lateinit var viewDataBinding: ViewDataBinding

    final override lateinit var viewModel: ViewModelType

    @get:LayoutRes
    abstract val layoutId: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory.getInstance(activity?.application!!)
        ).get(viewModelClass)
        lifecycle.addObserver(viewModel)

        viewDataBinding.let {
            val viewModelRId = viewModeRId
            if (viewModelRId != 0) {
                it.setVariable(viewModelRId, viewModel)
                it.lifecycleOwner = viewLifecycleOwner
                it.executePendingBindings()
            }
        }

        setViewModelArguments()
        setNavigationObserver()
        setDefaultErrorObserver()
        bindViewModel()
    }

    private fun setViewModelArguments() {
        arguments?.let { viewModel.arguments = it }
    }

    private fun setNavigationObserver() {
        viewModel.navigationEvent.observe(viewLifecycleOwner, Observer { navigation ->
            when (navigation) {
                is NavigationEvent.To ->
                    NavHostFragment.findNavController(this).navigate(navigation.directions)
                is NavigationEvent.Back ->
                    NavHostFragment.findNavController(this).navigateUp()
            }
        })
    }

    protected open fun setDefaultErrorObserver() {
        viewModel.error.observe(viewLifecycleOwner, Observer {
            when (it) {
                is DefaultError -> Toast.makeText(
                    context, getString(it.title) + "\n\n" + getString(it.description),
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}


/**
 * Represent View that can be bound to ViewModel
 */
interface BaseView<ViewModelType : BaseViewModel> {
    /**
     * Provides class of this views ViewModel
     * This is required to properly inject the ViewModel
     */
    val viewModelClass: Class<ViewModelType>

    /**
     * Provides name of view model (BR.name)
     */
    val viewModeRId: Int

    /**
     * Provides ViewModel of this view
     * ViewModel will be injected into the view and returned from this method
     */
    var viewModel: ViewModelType

    /**
     * Invoked once everything is ready
     * Use this method to complete binding to view model
     * (all bindings that were not possible through XML)
     */
    fun bindViewModel()
}