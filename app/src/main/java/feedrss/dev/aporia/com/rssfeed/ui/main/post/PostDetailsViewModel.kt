package feedrss.dev.aporia.com.rssfeed.ui.main.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import feedrss.dev.aporia.com.rssfeed.data.model.Post
import feedrss.dev.aporia.com.rssfeed.data.repository.PostRepository
import feedrss.dev.aporia.com.rssfeed.di.Schedulers
import feedrss.dev.aporia.com.rssfeed.ui.base.AppError
import feedrss.dev.aporia.com.rssfeed.ui.base.BaseViewModel
import javax.inject.Inject

class PostDetailsViewModel @Inject constructor(
    private val postRepository: PostRepository,
    schedulers: Schedulers
) : BaseViewModel(schedulers) {

    private val postId: String by lazy {
        PostDetailsFragmentArgs.fromBundle(arguments).postId
    }

    private val _post = MutableLiveData<Post>()
    val post: LiveData<Post> = _post

    override fun onLifecycleOwnerResume() {
        super.onLifecycleOwnerResume()
        fetchPost()
    }

    fun onBookmarkClick() {
        if (post.value!!.bookmarked) {
            postRepository.unBookmarkPost(postId).uiSubscribe {
                _post.value = post.value?.copy(
                    bookmarked = false
                )
            }
        } else {
            postRepository.bookmarkPost(postId).uiSubscribe {
                _post.value = post.value?.copy(
                    bookmarked = true
                )
            }
        }
    }

    private fun fetchPost() {
        postRepository.getPost(postId).uiSubscribe {
            _post.value = it
        }
    }
}