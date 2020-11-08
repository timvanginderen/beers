package be.tim.beers.brewery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import be.tim.beers.R
import be.tim.beers.util.Util
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class BreweryMapFragment : Fragment(), OnMapReadyCallback {

    private val TAG = BreweryMapFragment::class.qualifiedName

    private val args: BreweryMapFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.brewery_map_frag, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = args.name

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        Log.d(TAG, "onMapReady")
        googleMap?.apply {
            val brewery = LatLng(Util.convertLocationToDouble(args.lat),
                    Util.convertLocationToDouble(args.long))
            addMarker(
                MarkerOptions()
                    .position(brewery)
                    .title(args.name)
            )
            val cameraPosition = CameraPosition.builder().target(brewery).zoom(15F).build()
            animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }
    }

}