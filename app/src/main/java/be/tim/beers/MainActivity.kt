package be.tim.beers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import be.tim.beers.beerdetail.BeerDetailFragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.navigation.NavigationView
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private val TAG = MainActivity::class.qualifiedName

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebaseAnalytics = Firebase.analytics

        setSupportActionBar(findViewById(R.id.toolbar))
        setupNavigationDrawer()

        val navController: NavController = findNavController(R.id.fragment)
        appBarConfiguration =
                // TODO: 06/11/2020 fix deprecated code
                AppBarConfiguration.Builder(R.id.beersFragment)
                        .setDrawerLayout(drawerLayout)
                        .build()
        setupActionBarWithNavController(navController, appBarConfiguration)
        findViewById<NavigationView>(R.id.nav_view)
                .setupWithNavController(navController)

        // Get the SupportMapFragment and request notification when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

//        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
//        val googleMap = mapFragment.awaitMap()

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

    override fun onMapReady(googleMap: GoogleMap?) {
        Log.d(TAG, "onMapReady called")
        googleMap?.apply {
            val sydney = LatLng(-33.852, 151.211)
            addMarker(
                MarkerOptions()
                    .position(sydney)
                    .title("Marker in Sydney")
            )
        }
    }
}