package feedrss.dev.aporia.com.rssfeed.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import feedrss.dev.aporia.com.rssfeed.OnFragmentInteractionListener
import feedrss.dev.aporia.com.rssfeed.R
import feedrss.dev.aporia.com.rssfeed.viewmodel.CategoriesViewModel

class CategoriesFragment : BaseFragment<CategoriesViewModel>() {
    private var listener: OnFragmentInteractionListener? = null

    override val layoutId: Int
        get() = R.layout.fragment_categories
    override val viewModelClass: Class<CategoriesViewModel>
        get() = CategoriesViewModel::class.java

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
