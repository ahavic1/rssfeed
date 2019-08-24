package feedrss.dev.aporia.com.rssfeed.ui.base

import android.os.Bundle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import feedrss.dev.aporia.com.rssfeed.common.disposeWith
import feedrss.dev.aporia.com.rssfeed.common.mapApiError
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import feedrss.dev.aporia.com.rssfeed.R

abstract class BaseViewModel(private val schedulers: Schedulers) : ViewModel(),
    LifecycleObserver {

    /**
     * Disposable container that can hold multiple disposable objects
     */
    private val disposables = CompositeDisposable()

    /**
     * Input fragment arguments set after ViewModel is created.
     */
    var arguments: Bundle = Bundle()

    /**
     * Navigation event observed from BaseFragment.
     */
    private val _navigationEvent = SingleLiveEvent<NavigationEvent>()
    val navigationEvent: SingleLiveEvent<NavigationEvent> = _navigationEvent

    private val _error = SingleLiveEvent<BaseError>()
    val error: LiveData<BaseError> = _error

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    protected open fun onLifecycleOwnerResume() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    protected open fun onLifecycleOwnerPause() {
    }

    protected fun navigate(directions: NavDirections) {
        _navigationEvent.value = NavigationEvent.To(directions)
    }

    protected fun setError(error: BaseError) {
        _error.value = error
    }

    protected fun <T> Single<T>.uiSubscribe(block: (T) -> Unit) {
        subscribeOn(schedulers.io())
            .observeOn(schedulers.main())
            .subscribe({
                block(it)
            }, {
                setError(mapApiError(it))
            }).disposeWith(disposables)
    }

    protected fun <T> Observable<T>.uiSubscribe(block: (T) -> Unit) {
        subscribeOn(schedulers.io())
            .observeOn(schedulers.main())
            .subscribe({
                block(it)
            }, {
                setError(mapApiError(it))
            }).disposeWith(disposables)
    }
}

sealed class NavigationEvent {
    data class To(val directions: NavDirections) : NavigationEvent()
    object Back : NavigationEvent()
}

sealed class BaseError(val title: Int, val description: Int) {
    abstract class FeatureError(title: Int, description: Int): BaseError(title, description)
}

object DefaultError: BaseError(R.string.all_error_title, R.string.all_error_description)
object HttpError: BaseError(R.string.all_error_title, R.string.all_error_description)