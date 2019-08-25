package feedrss.dev.aporia.com.rssfeed.ui.main.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import feedrss.dev.aporia.com.rssfeed.data.model.Post
import feedrss.dev.aporia.com.rssfeed.data.repository.PostRepository
import feedrss.dev.aporia.com.rssfeed.di.Schedulers
import feedrss.dev.aporia.com.rssfeed.ui.base.BaseViewModel
import feedrss.dev.aporia.com.rssfeed.ui.main.HomeFragmentDirections
import javax.inject.Inject

class PostsViewModel @Inject constructor(
    private var postRepository: PostRepository,
    schedulers: Schedulers
) : BaseViewModel(schedulers) {

    private val postList = mutableListOf<Post>()

    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> = _posts

    private val _bookmarkedPosts = MutableLiveData<List<Post>>()
    val bookmarkedPosts: LiveData<List<Post>> = _bookmarkedPosts

    init {
        // Testing
        postRepository.populateDB().uiSubscribe {}
        fetchPosts()
    }

    override fun onLifecycleOwnerResume() {
        super.onLifecycleOwnerResume()
        fetchPosts()
    }

    fun refreshPosts() {
        fetchPosts()
    }

    fun onPostSelected(post: Post) {
        navigate(HomeFragmentDirections.actionHomeToPostDetails(post.id))
    }

    fun onBookmarkItem(post: Post) {
        postRepository.bookmarkPost(post.id).uiSubscribe {
            fetchPosts()
        }
    }

    fun unBookmarkPost(post: Post) {
        postRepository.unBookmarkPost(post.id).uiSubscribe {
            fetchPosts()
        }
    }

    fun searchPosts(queryText: CharSequence) {
        if (queryText.isEmpty()) {
            _posts.value = postList
            _bookmarkedPosts.value = postList
        } else {
            val filtered = postList.filter {
                it.title.startsWith(queryText, true) ||
                    it.description.startsWith(queryText, true)
            }
            _posts.value = filtered
            _bookmarkedPosts.value = filtered
        }
    }

    private fun fetchPosts() {
        postRepository.getPosts().uiSubscribe { posts ->
            postList.clear()
            postList.addAll(posts)
            _posts.value = posts
            _bookmarkedPosts.value = posts.filter { it.bookmarked }
        }
    }
}
