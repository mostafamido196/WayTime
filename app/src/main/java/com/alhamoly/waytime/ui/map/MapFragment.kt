package com.alhamoly.waytime.ui.map

//import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.alhamoly.waytime.R
import com.alhamoly.waytime.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback {

//  https://www.youtube.com/watch?v=eiexkzCI8m8&t=0s
//  https://www.geeksforgeeks.org/how-to-implement-google-map-inside-fragment-in-android/

/*
                   //todo Mostafa
    1- dependencies from -->
        https://developers.google.com/maps/documentation/android-sdk/start
    2- create api_key
        2.1- search in google : google console api
        2.2- choose library from side bar
        2.3- from top action bar select the project and create a new project from the dialog
        2.4- choose library from side bar
        2.5- choose map sdk for android --> then Enable
        2.6- choose option menu(left top) -> APIs & Services -> credentials -> create credentials -> API key
        2.7- copy api key and save in android as a string
        or watching the video: https://www.youtube.com/watch?v=-g9G6TRQq-E

    3- add permissions and meta data in manifests
    4- open xml and add name in fragment tag : com.google.android.gms.maps.SupportMapFragment
    5- add code in your fragment or activity كلٌُ على حدى


 */

    private lateinit var binding: FragmentMapBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setup()
    }

    private fun setup() {

        // TODO: testing git
        prepareMap()
        binding.btn.setOnClickListener { mMap.mapType = GoogleMap.MAP_TYPE_HYBRID }

    }

    private lateinit var mMap: GoogleMap

    private fun prepareMap() {

        // Initialize map fragment
        val supportMapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        // Async map
        supportMapFragment!!.getMapAsync(this)//when map is ready -> see override onMapReady fn
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
//        mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
        setOnClickMap(mMap)

    }

    private fun setOnClickMap(googleMap: GoogleMap) {
        googleMap.setOnMapClickListener { latLng -> // When clicked on map
            // Initialize marker options
            val markerOptions = MarkerOptions()
            // Set position of marker
            markerOptions.position(latLng)
            // Set title of marker
            markerOptions.title(latLng.latitude.toString() + " : " + latLng.longitude)
            // Remove all marker
            googleMap.clear()
            // Animating to zoom the marker
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
            // Add marker on map
            googleMap.addMarker(markerOptions)
        }
    }


}