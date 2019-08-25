package feedrss.dev.aporia.com.rssfeed.ui.main.post

import android.annotation.SuppressLint
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding2.widget.RxSearchView
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import feedrss.dev.aporia.com.rssfeed.R
import feedrss.dev.aporia.com.rssfeed.ui.base.BaseFragment
import feedrss.dev.aporia.com.rssfeed.ui.base.ViewModelKey
import kotlinx.android.synthetic.main.fragment_posts.recyclerView
import kotlinx.android.synthetic.main.fragment_posts.searchView
import kotlinx.android.synthetic.main.fragment_posts.swipeRefreshLayout

class PostsFragment : BaseFragment<PostsViewModel>() {

    private val postsAdapter by lazy {
        PostAdapter(viewModel, ArrayList(0))
    }

    override val layoutId: Int
        get() = R.layout.fragment_posts
    override val viewModelClass: Class<PostsViewModel>
        get() = PostsViewModel::class.java
    override val viewModeRId: Int
        get() = 0

    override fun bindViewModel() {
        setupUI()

        viewModel.posts.observe(viewLifecycleOwner, Observer {
            it?.let {
                swipeRefreshLayout.isRefreshing = false
                postsAdapter.update(it)
            }
        })
    }

    @SuppressLint("CheckResult")
    private fun setupUI() {
        recyclerView.run {
            layoutManager = LinearLayoutManager(activity)
            itemAnimator = DefaultItemAnimator()
            adapter = postsAdapter
        }

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            viewModel.refreshPosts()
        }

        RxSearchView.queryTextChangeEvents(searchView)
            .skipWhile {
                it.queryText().length < 3
            }.subscribe {
                viewModel.searchPosts(it.queryText())
            }
    }

    companion object {
        @JvmStatic
        fun newInstance() = PostsFragment()
    }
}

@Module
abstract class PostsModule {

    @Binds
    @IntoMap
    @ViewModelKey(PostsViewModel::class)
    abstract fun providePostsViewModel(viewModel: PostsViewModel): ViewModel
}
