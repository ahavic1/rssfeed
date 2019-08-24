package feedrss.dev.aporia.com.rssfeed.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import feedrss.dev.aporia.com.rssfeed.AppError
import feedrss.dev.aporia.com.rssfeed.BaseViewModel
import feedrss.dev.aporia.com.rssfeed.Schedulers
import feedrss.dev.aporia.com.rssfeed.data.model.Post
import feedrss.dev.aporia.com.rssfeed.data.repository.PostRepository

class ListViewModel(private var postRepository: PostRepository,
                    schedulers: Schedulers): BaseViewModel(schedulers) {

    private val postList = mutableListOf<Post>()

    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> = _posts

    private val _bookmarkedPosts = MutableLiveData<List<Post>>()
    val bookmarkedPosts: LiveData<List<Post>> = _bookmarkedPosts

    val postObservable = MutableLiveData<Post>()
    val errorObservable = MutableLiveData<AppError>()

    init {
        fetchPosts()
    }

    override fun onLifecycleOwnerResume() {
        super.onLifecycleOwnerResume()
        fetchPosts()
    }

    private fun fetchPosts() {
        postRepository.getPosts().uiSubscribe({ posts ->
            postList.clear()
            postList.addAll(posts)
            _posts.value = posts
            _bookmarkedPosts.value = posts.filter { it.bookmarked }
        }, errorObservable)
    }

    fun refreshPosts() {
        fetchPosts()
    }

    fun onItemClick(post: Post) {
        postObservable.value = post
    }

    fun onBookmarkItem(post: Post) {
        postRepository.bookmarkPost(post.id).uiSubscribe({
            fetchPosts()
        }, errorObservable)
    }

    fun unBookmarkPost(post: Post) {
        postRepository.unBookmarkPost(post.id).uiSubscribe({
            fetchPosts()
        }, errorObservable)
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
}
