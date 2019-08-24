package feedrss.dev.aporia.com.rssfeed.ui.main.category

import feedrss.dev.aporia.com.rssfeed.R
import feedrss.dev.aporia.com.rssfeed.ui.base.BaseFragment

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
