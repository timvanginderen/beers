package be.tim.beers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import be.tim.beers.beers.BeersFragment
import be.tim.beers.beers.BeersViewModel
import be.tim.beers.data.Beer
import be.tim.beers.di.Injection
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.qualifiedName


    private lateinit var viewModel: BeersViewModel

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()

        setSupportActionBar(findViewById(R.id.toolbar))
        setupNavigationDrawer()
        setSupportActionBar(findViewById(R.id.toolbar))

        val navController: NavController = findNavController(R.id.fragment)
        appBarConfiguration =
                // TODO: 06/11/2020 fix deprecated code
                AppBarConfiguration.Builder(R.id.beersFragment)
                        .setDrawerLayout(drawerLayout)
                        .build()
        setupActionBarWithNavController(navController, appBarConfiguration)
        findViewById<NavigationView>(R.id.nav_view)
                .setupWithNavController(navController)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            Injection.provideViewModelFactory()
        ).get(BeersViewModel::class.java)

        viewModel.museums.observe(this, renderBeers)
//        viewModel.isViewLoading.observe(this, isViewLoadingObserver)
//        viewModel.onMessageError.observe(this, onMessageErrorObserver)
//        viewModel.isEmptyList.observe(this, emptyListObserver)
    }

    //observers
    private val renderBeers = Observer<List<Beer>> {
        Log.v(TAG, "data updated $it")
//        layoutError.visibility = View.GONE
//        layoutEmpty.visibility = View.GONE
//        adapter.update(it)
    }


    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.fragment).navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    private fun setupNavigationDrawer() {
        drawerLayout = (findViewById<DrawerLayout>(R.id.drawer_layout))
                .apply {
                    setStatusBarBackground(R.color.brown_600)
                }
    }
}