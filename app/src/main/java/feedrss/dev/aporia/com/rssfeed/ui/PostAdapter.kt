package feedrss.dev.aporia.com.rssfeed.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import feedrss.dev.aporia.com.rssfeed.R
import feedrss.dev.aporia.com.rssfeed.data.model.Post
import feedrss.dev.aporia.com.rssfeed.viewmodel.ListViewModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.post_list_item_layout.*

class PostAdapter(private val viewModel: ListViewModel,
                  private var posts: ArrayList<Post>):
        RecyclerView.Adapter<PostAdapter.ViewHolder>(), Filterable {

    private var postsFiltered: ArrayList<Post> = posts

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_list_item_layout,
                                                                        parent,
                                                            false)
        return ViewHolder(view, viewModel)
    }

    override fun getItemCount() = postsFiltered.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
            holder.bindTo(postsFiltered[position])

    fun update(data: List<Post>) {
        posts.clear()
        posts.addAll(data)
        postsFiltered = posts
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val searchQuery = p0.toString()
                postsFiltered = if (searchQuery.isEmpty()) posts
                else {
                    val filteredLists = ArrayList<Post>()
                    for (post in posts) {
                        if (post.title.startsWith(searchQuery, true))
                            filteredLists.add(post)
                        else if (post.description.startsWith(searchQuery, true))
                            filteredLists.add(post)
                    }
                    filteredLists
                }
                val filterResult = FilterResults()
                filterResult.values = postsFiltered
                return  filterResult
            }

            override fun publishResults(p0: CharSequence?, filterResults: FilterResults?) {
                postsFiltered = filterResults!!.values as ArrayList<Post>
                notifyDataSetChanged()
            }
        }
    }


    class ViewHolder(override val containerView: View,
                     private val viewModel: ListViewModel):
            RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindTo(post: Post) {
            with(post) {
                itemTitle.text = title
                itemDescription.text = description

                if (bookmarked) bookmarkedIcon.setImageResource(R.drawable.ic_bookmark_fill)
                else bookmarkedIcon.setImageResource(R.drawable.ic_bookmark)

                bookmarkedIcon.setOnClickListener {
                    if (post.bookmarked) {
                        post.bookmarked = false
                        viewModel.unBookmarkPost(post)
                    } else {
                        post.bookmarked = true
                        viewModel.onBookmarkItem(post)
                    }
                }
                containerView.setOnClickListener { viewModel.onItemClick(post) }
            }
        }
    }

}
