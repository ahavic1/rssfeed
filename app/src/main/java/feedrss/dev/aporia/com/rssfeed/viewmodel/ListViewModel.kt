package feedrss.dev.aporia.com.rssfeed.viewmodel

import androidx.lifecycle.MutableLiveData
import feedrss.dev.aporia.com.rssfeed.AppError
import feedrss.dev.aporia.com.rssfeed.BaseViewModel
import feedrss.dev.aporia.com.rssfeed.SchedulersWrapper
import feedrss.dev.aporia.com.rssfeed.data.model.Post
import feedrss.dev.aporia.com.rssfeed.data.repository.PostRepository
import feedrss.dev.aporia.com.rssfeed.extensions.disposeWith
import feedrss.dev.aporia.com.rssfeed.extensions.uiSubscribe

class ListViewModel(private var postRepository: PostRepository,
                    schedulers: SchedulersWrapper): BaseViewModel(schedulers) {

    val postsObservable = MutableLiveData<List<Post>>()
    val bookmarkedPostsObservable = MutableLiveData<List<Post>>()
    val postObservable = MutableLiveData<Post>()
    val errorObservable = MutableLiveData<AppError>()

    init {
        fetchPosts()
        fetchBookmarkedPosts()
    }

    fun refresh() {
        fetchPosts()
        fetchBookmarkedPosts()
    }

    private fun fetchPosts() {
        postRepository.getPosts().uiSubscribe({
            postsObservable.value = it
        }, errorObservable, schedulers).disposeWith(disposeBag)
    }

    private fun fetchBookmarkedPosts() {
        postRepository.getBookmarkedPosts().uiSubscribe({
            bookmarkedPostsObservable.value = it
        }, errorObservable, schedulers).disposeWith(disposeBag)
    }

    fun onItemClick(post: Post) {
        postObservable.value = post
    }

    fun onBookmarkItem(post: Post) {
        postRepository.bookmarkPost(post.id).uiSubscribe({
            refresh()
        }, errorObservable, schedulers).disposeWith(disposeBag)
    }

    fun unBookmarkPost(post: Post) {
        postRepository.unBookmarkPost(post.id).uiSubscribe({
            refresh()
        }, errorObservable, schedulers).disposeWith(disposeBag)
    }
}
