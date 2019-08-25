package feedrss.dev.aporia.com.rssfeed

import android.app.Activity
import android.app.Application
import android.content.Context
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.DaggerApplication
import feedrss.dev.aporia.com.rssfeed.di.DaggerAppComponent
import javax.inject.Inject

class App: DaggerApplication() {

    @JvmField
    @Inject
    var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>? = null

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }

    companion object {
        private lateinit var refWatcher: RefWatcher
        fun getRefWatcher(context: Context) = refWatcher
    }

    override fun onCreate() {
        super.onCreate()

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        refWatcher = LeakCanary.install(this)

        DaggerAppComponent.builder().create(this)
    }
}