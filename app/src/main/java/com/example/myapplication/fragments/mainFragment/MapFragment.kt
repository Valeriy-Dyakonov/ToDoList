package com.example.myapplication.fragments.mainFragment

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentMapBinding
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions


class MapFragment : Fragment(), OnMapReadyCallback {
    private lateinit var map: MapView
    private lateinit var binding: FragmentMapBinding
    private lateinit var gMap: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var points = ArrayList<LatLng>()
    private var polyline: Polyline? = null
    private var isTrackingStep: Boolean = false

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            val latitude = p0.lastLocation.latitude
            val longitude = p0.lastLocation.longitude
            if (points.isEmpty() || latitude != points.last().latitude || longitude != points.last().longitude) {
                points.add(LatLng(latitude, longitude))
                addPolyline()
            }
        }
    }
    private val locationRequest = LocationRequest.create().apply {
        interval = 10_000
        fastestInterval = 5_000
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater)
        map = binding.mapView
        map.onCreate(savedInstanceState)
        map.getMapAsync(this)
        initListeners()
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = MapFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
    }

    private fun initListeners() {
        binding.apply {
            clearLineButton.setOnClickListener {
                points.clear()
                polyline?.remove()
                gMap.addPolyline(PolylineOptions())
                clearLineButton.visibility = View.INVISIBLE
            }
            gpsTracker.setBackgroundColor(if (isTrackingStep) Color.rgb(166, 23, 0) else  Color.rgb(0, 185, 69))
            gpsTracker.setOnClickListener {
                isTrackingStep = !isTrackingStep
                gpsTracker.setBackgroundColor(if (isTrackingStep) Color.rgb(166, 23, 0) else  Color.rgb(0, 185, 69))
                if (isTrackingStep) {
                    checkPermission()
                    points.clear()
                    setLocationListener()
                } else {
                    fusedLocationProviderClient.removeLocationUpdates(locationCallback)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onStart() {
        super.onStart()
        map.onStart()
    }

    override fun onStop() {
        super.onStop()
        map.onStop()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        map.onDestroy()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        map.onLowMemory()
    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1
            )
            return
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        checkPermission()
        googleMap.isMyLocationEnabled = true
        fusedLocationProviderClient.lastLocation.addOnCompleteListener(requireActivity()) { task ->
            task.result?.let {
                googleMap.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(it.latitude, it.longitude), 15F
                    )
                )
            }
        }
        gMap = googleMap
        gMap.setOnMapLongClickListener {
            points.add(it)
            addPolyline()
        }
        if (isTrackingStep) {
            setLocationListener()
        }
    }

    private fun setLocationListener() {
        checkPermission()
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun addPolyline() {
        polyline?.remove()
        polyline = gMap.addPolyline(PolylineOptions().addAll(points))
        polyline!!.color = ContextCompat.getColor(requireContext(), R.color.color_highlight)
        if (points.size == 2) {
            binding.clearLineButton.visibility = View.VISIBLE
        }
    }
}