package feedrss.dev.aporia.com.rssfeed

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel(protected  var schedulers: SchedulersWrapper): ViewModel() {

    protected val disposeBag = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposeBag.clear()
    }
}
