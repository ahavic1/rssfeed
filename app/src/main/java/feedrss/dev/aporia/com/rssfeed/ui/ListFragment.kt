package feedrss.dev.aporia.com.rssfeed.ui

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding2.widget.RxSearchView
import feedrss.dev.aporia.com.rssfeed.OnFragmentInteractionListener
import feedrss.dev.aporia.com.rssfeed.R
import feedrss.dev.aporia.com.rssfeed.data.model.Post
import feedrss.dev.aporia.com.rssfeed.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.recyclerView
import kotlinx.android.synthetic.main.fragment_list.searchView
import kotlinx.android.synthetic.main.fragment_list.swipeRefreshLayout

class ListFragment : BaseFragment<ListViewModel>() {

    private var listener: OnFragmentInteractionListener? = null
    private val postsAdapter by lazy {
        PostAdapter(viewModel, ArrayList(0))
    }

    override val layoutId: Int = R.layout.fragment_list
    override val viewModelClass: Class<ListViewModel> = ListViewModel::class.java

    @SuppressLint("CheckResult")
    override fun bindViewModel() {
        viewModel.posts.observe(this@ListFragment, Observer {
            it?.let {
                swipeRefreshLayout.isRefreshing = false
                postsAdapter.update(it)
            }
        })

        viewModel.postObservable.observe(this@ListFragment, Observer {
            it?.let { openDetails(it) }
        })

        viewModel.errorObservable.observe(this@ListFragment, Observer {
            it?.let { onError(it) }
        })

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
            }
            .subscribe {
                viewModel.searchPosts(it.queryText())
            }
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


    private fun openDetails(post: Post) {

    }

    companion object {
        @JvmStatic
        fun newInstance() = ListFragment()
    }
}
