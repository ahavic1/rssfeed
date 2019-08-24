package feedrss.dev.aporia.com.rssfeed.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import feedrss.dev.aporia.com.rssfeed.ui.base.OnFragmentInteractionListener
import feedrss.dev.aporia.com.rssfeed.R
import feedrss.dev.aporia.com.rssfeed.common.replaceFragment
import feedrss.dev.aporia.com.rssfeed.ui.main.feed.FeedsFragment
import feedrss.dev.aporia.com.rssfeed.ui.main.post.BookmarksFragment
import feedrss.dev.aporia.com.rssfeed.ui.main.post.PostsFragment


class HomeFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var bottomNavBar: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_home, container, false)
        bottomNavBar = v.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnNavBarClickListener()
    }

    private fun setOnNavBarClickListener() {
        bottomNavBar.setOnNavigationItemSelectedListener {
            val selectedFragment: Fragment = when {
                it.itemId == R.id.actionList -> PostsFragment.newInstance()
                it.itemId == R.id.actionBookmarked -> BookmarksFragment.newInstance()
                it.itemId == R.id.actionCategory -> FeedsFragment.newInstance()
                else -> PostsFragment.newInstance()
            }
            activity?.supportFragmentManager?.replaceFragment(R.id.frame_layout, selectedFragment)
            true
        }

        bottomNavBar.menu.findItem(R.id.actionList).isChecked = true
        activity?.supportFragmentManager?.replaceFragment(R.id.frame_layout,
            PostsFragment.newInstance()
        )
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
        fun newInstance() = HomeFragment()
    }
}
