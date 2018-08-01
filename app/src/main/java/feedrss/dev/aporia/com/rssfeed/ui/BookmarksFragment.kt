package feedrss.dev.aporia.com.rssfeed.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding2.widget.RxSearchView
import feedrss.dev.aporia.com.rssfeed.OnFragmentInteractionListener
import feedrss.dev.aporia.com.rssfeed.R
import feedrss.dev.aporia.com.rssfeed.data.model.Post
import feedrss.dev.aporia.com.rssfeed.extensions.obtainViewModel
import feedrss.dev.aporia.com.rssfeed.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*

class BookmarksFragment: BaseFragment() {

    private var listener: OnFragmentInteractionListener? = null
    private lateinit var viewModel: ListViewModel
    private val postsAdapter by lazy {
        PostAdapter(viewModel, ArrayList(0))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = obtainViewModel(ListViewModel::class.java).apply {

            bookmarkedPostsObservable.observe(this@BookmarksFragment, Observer {
                it?.let {
                    swipeRefreshLayout.isRefreshing = false
                    postsAdapter.update(it)
                }
            })

            errorObservable.observe(this@BookmarksFragment, Observer {
                it?.let { onError(it) }
            })

            postObservable.observe(this@BookmarksFragment, Observer {
                it?.let { openDetails(it) }
            })
        }

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