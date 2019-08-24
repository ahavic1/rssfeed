package feedrss.dev.aporia.com.rssfeed.extensions

import androidx.lifecycle.MutableLiveData
import feedrss.dev.aporia.com.rssfeed.AppError
import feedrss.dev.aporia.com.rssfeed.Schedulers
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import retrofit2.HttpException

fun  <T> Single<T>.uiSubscribe(onSuccess: (T) -> Unit,
                               exceptionObservable: MutableLiveData<AppError>,
                               schedulers: Schedulers): Disposable {
    return subscribeOn(schedulers.io())
            .observeOn(schedulers.main())
            .subscribe({
                onSuccess.invoke(it)
            }, {
                val appError = handleError(it)
                exceptionObservable.value = appError
            })
}

fun  <T> Observable<T>.uiSubscribe(onSuccess: (T) -> Unit,
                                   exceptionObservable: MutableLiveData<AppError>,
                                   schedulers: Schedulers): Disposable {
    return subscribeOn(schedulers.io())
            .observeOn(schedulers.main())
            .subscribe({
                onSuccess.invoke(it)
            }, {
                val appError = handleError(it)
                exceptionObservable.value = appError
            })
}


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