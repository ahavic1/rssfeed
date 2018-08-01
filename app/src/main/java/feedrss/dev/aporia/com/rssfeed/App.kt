package feedrss.dev.aporia.com.rssfeed

import android.app.Application
import android.content.Context
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher



class App: Application() {

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
    }

}