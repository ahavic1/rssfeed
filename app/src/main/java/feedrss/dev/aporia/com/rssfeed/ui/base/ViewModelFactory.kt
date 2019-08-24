package feedrss.dev.aporia.com.rssfeed.ui.base

import android.annotation.SuppressLint
import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import feedrss.dev.aporia.com.rssfeed.data.repository.FeedRepository
import feedrss.dev.aporia.com.rssfeed.data.repository.PostRepository
import feedrss.dev.aporia.com.rssfeed.di.Injection
import feedrss.dev.aporia.com.rssfeed.ui.main.feed.AddFeedViewModel
import feedrss.dev.aporia.com.rssfeed.ui.main.feed.FeedsViewModel
import feedrss.dev.aporia.com.rssfeed.ui.main.post.PostDetailsViewModel
import feedrss.dev.aporia.com.rssfeed.ui.main.post.PostsViewModel

class ViewModelFactory private constructor(
    private val application: Application,
    private val schedulers: Schedulers,
    private val postsRepository: PostRepository,
    private val feedRepository: FeedRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
            with(modelClass) {
                when {
                    isAssignableFrom(PostsViewModel::class.java) ->
                        PostsViewModel(postsRepository,
                            schedulers
                        )
                    isAssignableFrom(FeedsViewModel::class.java) ->
                        FeedsViewModel(feedRepository,
                            schedulers
                        )
                    isAssignableFrom(AddFeedViewModel::class.java) ->
                        AddFeedViewModel(feedRepository,
                            schedulers
                        )
                    isAssignableFrom(PostDetailsViewModel::class.java) ->
                        PostDetailsViewModel(postsRepository,
                            schedulers
                        )
                    else ->
                        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                }
            } as T

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application) =
                INSTANCE ?: synchronized(
                    ViewModelFactory::class.java) {
                    INSTANCE
                        ?: ViewModelFactory(application,
                            Schedulers(),
                            Injection.providePostRepository(application.applicationContext),
                            Injection.provideFeedRepository(application.applicationContext)
                        )
                            .also { INSTANCE = it }
                }


        @VisibleForTesting
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}