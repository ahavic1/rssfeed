package feedrss.dev.aporia.com.rssfeed.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import feedrss.dev.aporia.com.rssfeed.AppError
import feedrss.dev.aporia.com.rssfeed.viewmodel.ViewModelFactory

abstract class BaseFragment<ViewModelType : ViewModel> : Fragment() {

    protected lateinit var viewModel: ViewModelType

    abstract val layoutId: Int
    abstract val viewModelClass: Class<ViewModelType>
    abstract fun bindViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory.getInstance(activity?.application!!)
        ).get(viewModelClass)
        bindViewModel()
    }

    fun onError(error: AppError) {
        //do something with it
    }
}