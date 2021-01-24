package com.qads.qedhex.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firestore.v1.WriteResult;
import com.qads.qedhex.R;
import com.qads.qedhex.activities.MapsActivity;
import com.qads.qedhex.activities.LoginActivity;
import com.qads.qedhex.activities.SignUpActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends Fragment {
    private RecyclerView eventsView;
    private View rootView;
    private Toolbar toolbar, loadedToolbar;
    private RelativeLayout relativeLayout;
    private CardView loadedCardView, searchBar;
    private ImageView cartImageButton;
    private SeekBar seekBar;
    private TextView textView;
    private int walkTime;
    private ImageView goButton;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<Double> location = new ArrayList<>();
    private String accessToken;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.location_fragment, container, false);

        seekBar = rootView.findViewById(R.id.seekBar);
        textView = rootView.findViewById(R.id.minsText);
        goButton = rootView.findViewById(R.id.goButton);
        cartImageButton = (ImageView) rootView.findViewById(R.id.sign_out);
        cartImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "hehe", Toast.LENGTH_SHORT).show();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textView.setText("" + i);
                walkTime = i;
                //textView.setY(100); just added a value set this properly using screen with height aspect ratio , if you do not set it by default it will be there below seek bar
                //hehehe
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                startActivity(intent);
                // Add a new document in collection "cities"
//                accessToken = SignUpActivity.accessToken;
//                location.add(52.2152625);
//                location.add(0.1172745);
//
//                Map<String, Object> docData = new HashMap<>();
//                String calDocID = db.collection("calendar_slots").document().getId();   //Get Doc ID first.
//                docData.put("access_token", accessToken);
//                docData.put("min_time", walkTime);
//// Add a new document (asynchronously) in collection "cities" with id "LA"
//                db.collection("calendar_slots").document(calDocID).set(docData);
//
//                Map<String, Object> docData2 = new HashMap<>();
//                String mapDocID = db.collection("calendar_slots").document().getId();
//                docData.put("time_to_walk", walkTime);
//                docData.put("walk_speed", 1);
//                docData.put("location", location);
//// Add a new document (asynchronously) in collection "cities" with id "LA"
//                db.collection("walks").document(mapDocID).set(docData2);
//
//                Fragment nextFragment = new ItemsFragment();
//                Bundle bundle = new Bundle();
//                bundle.putString("CalID", calDocID);
//                bundle.putString("MapID", mapDocID);
//                nextFragment.setArguments(bundle);
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container, nextFragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
            }
        });

        signOut(rootView);

        //hehe

        return rootView;
    }

    public void signOut(View v) {
        //SIGN OUT METHOD
        cartImageButton = (ImageView) v.findViewById(R.id.sign_out);
        cartImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void onStop() {
        super.onStop();
    }

    private void setupCollapsingToolbar(View rootView){
        relativeLayout = rootView.findViewById(R.id.relative_layout);
        loadedCardView = rootView.findViewById(R.id.final_card_view);
        loadedToolbar = rootView.findViewById(R.id.final_toolbar);
    }
}
