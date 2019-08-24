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

class BookmarksFragment: BaseFragment<ListViewModel>() {

    private var listener: OnFragmentInteractionListener? = null
    private val postsAdapter by lazy {
        PostAdapter(viewModel, ArrayList(0))
    }

    override val layoutId: Int
        get() = R.layout.fragment_list
    override val viewModelClass: Class<ListViewModel>
        get() = ListViewModel::class.java

    override fun bindViewModel() {
        viewModel.bookmarkedPostsObservable.observe(viewLifecycleOwner, Observer {
            it?.let {
                swipeRefreshLayout.isRefreshing = false
                postsAdapter.update(it)
            }
        })

        viewModel.errorObservable.observe(viewLifecycleOwner, Observer {
            it?.let { onError(it) }
        })

        viewModel.postObservable.observe(viewLifecycleOwner, Observer {
            it?.let { openDetails(it) }
        })

        initRecyclerView()
        disableOnRefreshListener()
        setOnSearchListener()
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

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = postsAdapter
    }

    private fun disableOnRefreshListener() {
        swipeRefreshLayout.isEnabled = false
    }

    @SuppressLint("CheckResult")
    private fun setOnSearchListener() {
        RxSearchView.queryTextChangeEvents(searchView)
                .skipWhile {
                    it.queryText().length < 3
                }
                .subscribe {
                    postsAdapter.filter.filter(it.queryText())
                }
    }

    private fun openDetails(post: Post) {

    }

    companion object {
        @JvmStatic
        fun newInstance() = BookmarksFragment()
    }
}