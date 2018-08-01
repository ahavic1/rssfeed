package feedrss.dev.aporia.com.rssfeed

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SchedulersWrapper {
    fun io(): Scheduler {
        return Schedulers.io()
    }

    fun main(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}