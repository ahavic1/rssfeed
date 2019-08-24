package feedrss.dev.aporia.com.rssfeed.ui.main.post

import android.widget.ImageView
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import feedrss.dev.aporia.com.rssfeed.R
import feedrss.dev.aporia.com.rssfeed.data.model.Post
import feedrss.dev.aporia.com.rssfeed.data.repository.PostRepository
import feedrss.dev.aporia.com.rssfeed.ui.base.AppError
import feedrss.dev.aporia.com.rssfeed.ui.base.BaseFragment
import feedrss.dev.aporia.com.rssfeed.ui.base.BaseViewModel
import feedrss.dev.aporia.com.rssfeed.ui.base.Schedulers

class PostDetailsFragment : BaseFragment<PostDetailsViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_post_details
    override val viewModelClass: Class<PostDetailsViewModel>
        get() = PostDetailsViewModel::class.java
    override val viewModeRId: Int
        get() = BR.viewModel

    override fun bindViewModel() {
        viewModel.post.observe(viewLifecycleOwner, Observer { post ->
            view?.findViewById<ImageView>(R.id.bookmark)?.let {
                if (post.bookmarked) it.setImageResource(R.drawable.ic_bookmark_fill)
                else it.setImageResource(R.drawable.ic_bookmark)
            }
        })
    }
}

class PostDetailsViewModel(
    private val postRepository: PostRepository,
    schedulers: Schedulers
) : BaseViewModel(schedulers) {

    private val postId: String by lazy {
        PostDetailsFragmentArgs.fromBundle(arguments).postId
    }

    private val _post = MutableLiveData<Post>()
    val post: LiveData<Post> = _post

    val errorObservable = MutableLiveData<AppError>()

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