package feedrss.dev.aporia.com.rssfeed.di

import dagger.Binds
import dagger.Module
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject
import javax.inject.Singleton

@Module
abstract class SchedulersModule {

        @Binds
        @Singleton
        abstract fun provideSchedulers(schedulers: SchedulersImpl): Schedulers
}

class SchedulersImpl @Inject constructor(): Schedulers {
    override fun io(): Scheduler = io.reactivex.schedulers.Schedulers.io()

    override fun main(): Scheduler = AndroidSchedulers.mainThread()

    override fun computation(): Scheduler = io.reactivex.schedulers.Schedulers.computation()
}

interface Schedulers {
    fun io(): Scheduler
    fun main(): Scheduler
    fun computation(): Scheduler
}
