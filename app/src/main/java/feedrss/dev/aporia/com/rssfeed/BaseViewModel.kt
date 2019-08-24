package feedrss.dev.aporia.com.rssfeed

import androidx.lifecycle.*
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel(protected  var schedulers: Schedulers): ViewModel(), LifecycleObserver {

    protected val disposables = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    open protected fun onLifecycleOwnerResume() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    open protected fun onLifecycleOwnerPause() {
    }

    protected fun <T> Single<T>.uiSubscribe(block: (T) -> Unit, errorObservable: MutableLiveData<AppError>) {
        subscribeOn(schedulers.io())
            .observeOn(schedulers.main())
            .subscribe({
                block(it)
            }, {
                val appError = handleError(it)
                errorObservable.value = appError
            }).disposeWith(disposables)
    }

    protected fun <T> Observable<T>.uiSubscribe(block: (T) -> Unit, errorObservable: MutableLiveData<AppError>) {
        subscribeOn(schedulers.io())
            .observeOn(schedulers.main())
            .subscribe({
                block(it)
            }, {
                val appError = handleError(it)
                errorObservable.value = appError
            }).disposeWith(disposables)
    }
}
