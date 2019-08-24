package feedrss.dev.aporia.com.rssfeed.ui.main.post

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import feedrss.dev.aporia.com.rssfeed.R
import feedrss.dev.aporia.com.rssfeed.data.model.Post
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_post.bookmarkedIcon
import kotlinx.android.synthetic.main.item_post.itemDescription
import kotlinx.android.synthetic.main.item_post.itemTitle

class PostAdapter(
    private val viewModel: PostsViewModel,
    private var posts: ArrayList<Post>
) :
    RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_post,
            parent,
            false
        )
        return ViewHolder(view, viewModel)
    }

    override fun getItemCount() = posts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bindTo(posts[position])

    fun update(data: List<Post>) {
        posts.clear()
        posts.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(
        override val containerView: View,
        private val viewModel: PostsViewModel
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindTo(post: Post) {
            with(post) {
                itemTitle.text = title
                itemDescription.text = description

                if (bookmarked) bookmarkedIcon.setImageResource(R.drawable.ic_bookmark_fill)
                else bookmarkedIcon.setImageResource(R.drawable.ic_bookmark)

                bookmarkedIcon.setOnClickListener {
                    if (post.bookmarked) {
                        viewModel.unBookmarkPost(post)
                    } else {
                        viewModel.onBookmarkItem(post)
                    }
                }
                containerView.setOnClickListener { viewModel.onPostSelected(post) }
            }
        }
    }
}
