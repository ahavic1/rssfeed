package feedrss.dev.aporia.com.rssfeed.ui.base

import android.os.Bundle
import androidx.lifecycle.*
import androidx.navigation.NavDirections
import feedrss.dev.aporia.com.rssfeed.common.disposeWith
import feedrss.dev.aporia.com.rssfeed.common.handleError
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel(protected var schedulers: Schedulers) : ViewModel(),
    LifecycleObserver {


    private val _navigationEvent = SingleLiveEvent<NavigationEvent>()
    val navigationEvent: SingleLiveEvent<NavigationEvent> = _navigationEvent

    protected val disposables = CompositeDisposable()
    var arguments: Bundle = Bundle()

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

    protected fun navigate(directions: NavDirections) {
        _navigationEvent.value = NavigationEvent.To(directions)
    }

    protected fun <T> Single<T>.uiSubscribe(
        block: (T) -> Unit,
        errorObservable: MutableLiveData<AppError>
    ) {
        subscribeOn(schedulers.io())
            .observeOn(schedulers.main())
            .subscribe({
                block(it)
            }, {
                val appError = handleError(it)
                errorObservable.value = appError
            }).disposeWith(disposables)
    }

    protected fun <T> Observable<T>.uiSubscribe(
        block: (T) -> Unit,
        errorObservable: MutableLiveData<AppError>
    ) {
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

sealed class NavigationEvent() {
    data class To(val directions: NavDirections) : NavigationEvent()
    object Back : NavigationEvent()
}
