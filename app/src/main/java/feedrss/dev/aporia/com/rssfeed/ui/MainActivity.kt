package feedrss.dev.aporia.com.rssfeed.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import feedrss.dev.aporia.com.rssfeed.OnFragmentInteractionListener
import feedrss.dev.aporia.com.rssfeed.R

class MainActivity : AppCompatActivity(), OnFragmentInteractionListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
//        NavigationUI.setupWithNavController(bottomNav, findNavController(R.id.navHostFragment))
//        bottomNav.menu.getItem(1).isChecked = true
    }

    override fun onSupportNavigateUp() = findNavController(R.id.navHostFragment).navigateUp()

    override fun onFragmentInteraction(action: NavDirections) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
