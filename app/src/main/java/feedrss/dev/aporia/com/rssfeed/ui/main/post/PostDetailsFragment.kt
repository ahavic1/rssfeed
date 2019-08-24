package feedrss.dev.aporia.com.rssfeed.ui.main.post

import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
        postRepository.getPost(postId).uiSubscribe({
            _post.value = it
        }, errorObservable)
    }
}