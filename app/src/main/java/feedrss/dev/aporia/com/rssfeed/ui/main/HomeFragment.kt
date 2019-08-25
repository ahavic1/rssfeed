package feedrss.dev.aporia.com.rssfeed.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import feedrss.dev.aporia.com.rssfeed.R
import feedrss.dev.aporia.com.rssfeed.common.replaceFragment
import feedrss.dev.aporia.com.rssfeed.ui.main.feed.AddFeedFragment
import feedrss.dev.aporia.com.rssfeed.ui.main.feed.FeedsFragment
import feedrss.dev.aporia.com.rssfeed.ui.main.post.BookmarksFragment
import feedrss.dev.aporia.com.rssfeed.ui.main.post.PostsFragment


class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnNavBarClickListener(view.findViewById(R.id.bottomNavigationView))
    }

    private fun setOnNavBarClickListener(bottomNavBar: BottomNavigationView) {
        bottomNavBar.setOnNavigationItemSelectedListener {
            val selectedFragment = when (it.itemId) {
                R.id.actionHome -> PostsFragment.newInstance()
                R.id.actionBookmarked -> BookmarksFragment.newInstance()
                R.id.actionAddFeed -> AddFeedFragment.newInstance()
                R.id.actionCategory -> FeedsFragment.newInstance()
                R.id.actionSettings -> PostsFragment.newInstance()
                else -> PostsFragment.newInstance()
            }

            activity?.supportFragmentManager?.replaceFragment(R.id.frame_layout, selectedFragment)
            true
        }
        bottomNavBar.selectedItemId = R.id.actionHome
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}
