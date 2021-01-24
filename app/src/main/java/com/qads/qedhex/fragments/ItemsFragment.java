package com.qads.qedhex.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.qads.qedhex.activities.MapsActivity;
import com.qads.qedhex.helpers.CalendarItem;
import com.qads.qedhex.helpers.CalendarItemAdapter;
import com.qads.qedhex.R;
import com.qads.qedhex.helpers.InterestsAdapter;
import com.qads.qedhex.helpers.InterestsModel;
import com.qads.qedhex.helpers.Route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class ItemsFragment extends Fragment {
    private View rootView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference calendarItemRef = db.collection("calendar_slots");
    private CalendarItemAdapter adapter;
    private String start_time;
    private String end_time;
    private String calID;
    private String mapID;
    private ListenerRegistration calsReg;
    private DocumentReference cals;
    private CalendarItem calItem;
    private GridLayoutManager gridLayoutManager, all_gridLayoutManager;
    private RecyclerView.Adapter recyclerAdapter, all_recyclerAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Map<String, Object>> calsList = new ArrayList<>();
    private Map<String, Object> test = new HashMap<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @androidx.annotation.Nullable Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        rootView = inflater.inflate(R.layout.items_fragment, container, false);
        recyclerView = rootView.findViewById(R.id.items_rv);
        calID = bundle.getString("CalID");
        mapID = bundle.getString("MapID");

        test.put("start", "hehe");
        test.put("end", "hehehe");


//        calsList.add(test);


        setUpRecyclerView();
        return rootView;
    }

    private void setUpRecyclerView() {

        cals = db.collection("calendar_slots").document("i1UrOaFpNyCkd1PizfAp");
        calsReg = cals.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onEvent(@androidx.annotation.Nullable DocumentSnapshot value, @androidx.annotation.Nullable FirebaseFirestoreException error) {
                if (error != null) {

                    System.err.println("Listen failed: " + error);
                    return;
                }

                if (value != null && value.exists()) {

                    calItem = value.toObject(CalendarItem.class);
                    calsList.addAll(calItem.getResponse());
                    Toast.makeText(getContext(), calsList.toString(), Toast.LENGTH_SHORT).show();
                    CalendarItemAdapter adapter = new CalendarItemAdapter(calsList, new CalendarItemAdapter.OnNoteListener() {
                        @Override
                        public void onNoteClick(int position, TextView textView1, TextView textView2) {
                            Intent intent = new Intent(getActivity(), MapsActivity.class);
                            startActivity(intent);
                        }
                    });
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                }
            }
        });
    }
}