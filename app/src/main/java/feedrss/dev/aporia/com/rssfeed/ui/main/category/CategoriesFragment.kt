package feedrss.dev.aporia.com.rssfeed.ui.main.category

import android.content.Context
import feedrss.dev.aporia.com.rssfeed.ui.base.OnFragmentInteractionListener
import feedrss.dev.aporia.com.rssfeed.R
import feedrss.dev.aporia.com.rssfeed.ui.base.BaseFragment

class CategoriesFragment : BaseFragment<CategoriesViewModel>() {
    private var listener: OnFragmentInteractionListener? = null

    override val layoutId: Int
        get() = R.layout.fragment_categories
    override val viewModelClass: Class<CategoriesViewModel>
        get() = CategoriesViewModel::class.java
    override val viewModeRId: Int
        get() = 0

    override fun bindViewModel() {
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = CategoriesFragment()
    }
}
