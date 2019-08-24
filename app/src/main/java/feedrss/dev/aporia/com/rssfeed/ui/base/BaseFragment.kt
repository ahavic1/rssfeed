package feedrss.dev.aporia.com.rssfeed.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment

abstract class BaseFragment<ViewModelType : BaseViewModel> : Fragment() {

    protected lateinit var viewModel: ViewModelType
    private lateinit var viewDataBinding: ViewDataBinding

    abstract val layoutId: Int
    abstract val viewModelClass: Class<ViewModelType>
    abstract val viewModeRId: Int

    abstract fun bindViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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

    fun onError(error: AppError) {
        //do something with it
    }
}