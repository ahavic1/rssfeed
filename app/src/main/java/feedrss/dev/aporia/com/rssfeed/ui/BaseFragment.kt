package feedrss.dev.aporia.com.rssfeed.ui

import androidx.fragment.app.Fragment
import feedrss.dev.aporia.com.rssfeed.AppError

open class BaseFragment: Fragment() {


    fun onError(error: AppError) {
        //do something with it
    }

}