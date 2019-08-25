package feedrss.dev.aporia.com.rssfeed.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import dagger.android.DaggerActivity
import dagger.android.support.DaggerAppCompatActivity
import feedrss.dev.aporia.com.rssfeed.ui.base.OnFragmentInteractionListener
import feedrss.dev.aporia.com.rssfeed.R

class MainActivity : DaggerAppCompatActivity(), LifecycleOwner {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onSupportNavigateUp() = findNavController(R.id.navHostFragment).navigateUp()
}
