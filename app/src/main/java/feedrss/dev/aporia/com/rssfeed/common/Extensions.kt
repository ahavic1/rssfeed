package feedrss.dev.aporia.com.rssfeed.common

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import feedrss.dev.aporia.com.rssfeed.ui.base.AppError
import feedrss.dev.aporia.com.rssfeed.ui.base.ViewModelFactory
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import retrofit2.HttpException

// Fragment

fun <T : ViewModel> FragmentActivity.obtainViewModel(viewModelClass: Class<T>) =
    ViewModelProviders.of(this, ViewModelFactory.getInstance(application)).get(viewModelClass)

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

fun handleError(t: Throwable): AppError {
    return if (t is HttpException) {
        AppError(t.code(), t.message!!)
    } else {
        AppError(500, "Something went wrong")
    }
}
