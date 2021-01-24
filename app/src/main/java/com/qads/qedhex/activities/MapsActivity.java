package com.qads.qedhex.activities;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.qads.qedhex.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qads.qedhex.fragments.HomeFragment;
import com.qads.qedhex.helpers.Route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private GoogleMap mMap;
    private ListenerRegistration routesReg;
    private DocumentReference routes;
    private Route route;
    private List<LatLng> latlngList = new ArrayList<LatLng>();


    @Nullable
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        //getRoute(HomeFragment.mapDocID);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);




        final Button button = findViewById(R.id.new_route_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Map<String, Object> docData2 = new HashMap<>();
                HomeFragment.mapDocID = db.collection("walks").document().getId();
                docData2.put("time_to_walk", HomeFragment.walkTime);
                //System.out.println(docData2.put("time_to_walk", HomeFragment.walkTime));
                docData2.put("walk_speed", 1);
                docData2.put("location", HomeFragment.location);
// Add a new document (asynchronously) in collection "cities" with id "LA"
                db.collection("walks").document(HomeFragment.mapDocID).set(docData2);
                getRoute(HomeFragment.mapDocID);
            }
        });





    }

    public void getRoute(String docID) {

        routes = db.collection("walks").document(docID);
        routesReg = routes.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    System.err.println("Listen failed: " + error);
                    return;
                }

                if (value != null && value.exists()) {
                    route = value.toObject(Route.class);
                    for (Object  latlng : route.getSteps()){
                          Map<String, Object> llMap = (Map<String, Object>) latlng;
                          Double lat = (Double) llMap.get("lat");
                          Double lng = (Double) llMap.get("lng");
                          latlngList.add(new LatLng(lat,lng));

                        Polyline polyline1 = mMap.addPolyline(new PolylineOptions()
                                .clickable(true)
                                .addAll(latlngList));
//                        new LatLng(-35.016, 143.321),
//                        new LatLng(-34.747, 145.592),
//                        new LatLng(-34.364, 147.891),
//                        new LatLng(-33.501, 150.217),
//                        new LatLng(-32.306, 149.248),
//                        new LatLng(-32.491, 147.309)));
                        LatLng center = new LatLng(route.getResponseCenter().get("lat").doubleValue(), route.getResponseCenter().get("lng").doubleValue());
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center, 16));
                    }
                }
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Toast.makeText(getBaseContext(), latlngList.toString(), Toast.LENGTH_LONG).show();

        Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .addAll(latlngList));
//                        new LatLng(-35.016, 143.321),
//                        new LatLng(-34.747, 145.592),
//                        new LatLng(-34.364, 147.891),
//                        new LatLng(-33.501, 150.217),
//                        new LatLng(-32.306, 149.248),
//                        new LatLng(-32.491, 147.309)));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-23.684, 133.903), 4));
        // Add a marker in Sydney and move the camera
      //  LatLng sydney = new LatLng(-34, 151);
    //    mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
       // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}

//Map<String, Object> docData2 = new HashMap<>();
//                String mapDocID = db.collection("calendar_slots").document().getId();
//                docData.put("time_to_walk", walkTime);
//                docData.put("walk_speed", 1);
//                docData.put("location", location);
//// Add a new document (asynchronously) in collection "cities" with id "LA"
//                db.collection("walks").document(mapDocID).set(docData2);
