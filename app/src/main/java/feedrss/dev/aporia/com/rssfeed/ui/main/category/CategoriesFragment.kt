package feedrss.dev.aporia.com.rssfeed.ui.main.category

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import feedrss.dev.aporia.com.rssfeed.R
import feedrss.dev.aporia.com.rssfeed.ui.base.BaseFragment
import feedrss.dev.aporia.com.rssfeed.ui.base.ViewModelKey

class CategoriesFragment : BaseFragment<CategoriesViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_categories
    override val viewModelClass: Class<CategoriesViewModel>
        get() = CategoriesViewModel::class.java
    override val viewModeRId: Int
        get() = 0

    override fun bindViewModel() {
    }

    companion object {
        @JvmStatic
        fun newInstance() = CategoriesFragment()
    }
}

@Module
abstract class CategoriesModule {
    @Binds
    @IntoMap
    @ViewModelKey(CategoriesViewModel::class)
    abstract fun provideCategoriesViewModel(viewModel: CategoriesViewModel): ViewModel
}
