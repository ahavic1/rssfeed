package feedrss.dev.aporia.com.rssfeed.ui.main.post

import android.widget.ImageView
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import feedrss.dev.aporia.com.rssfeed.R
import feedrss.dev.aporia.com.rssfeed.ui.base.BaseFragment
import feedrss.dev.aporia.com.rssfeed.ui.base.ViewModelKey

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

@Module
abstract class PostDetailsModule {

    @Binds
    @IntoMap
    @ViewModelKey(PostDetailsViewModel::class)
    abstract fun providePostDetailsViewModel(viewModel: PostDetailsViewModel): ViewModel
}