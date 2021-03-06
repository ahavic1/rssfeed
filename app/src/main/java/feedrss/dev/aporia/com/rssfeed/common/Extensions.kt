package feedrss.dev.aporia.com.rssfeed.common

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import feedrss.dev.aporia.com.rssfeed.ui.base.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import retrofit2.HttpException

// Fragment

fun FragmentManager.replaceFragment(layoutId: Int, fragment: Fragment) {
    this.beginTransaction().apply {
        replace(layoutId, fragment)
        commit()
    }
}


// Rx

fun Disposable.disposeWith(disposable: CompositeDisposable) {
    disposable.add(this)
}

fun mapApiError(t: Throwable): BaseError {
    return if (t is HttpException) {
        HttpError
    } else {
        DefaultError
    }
}
