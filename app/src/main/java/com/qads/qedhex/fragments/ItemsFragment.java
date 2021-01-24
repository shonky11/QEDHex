package com.qads.qedhex.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.qads.qedhex.helpers.CalendarItem;
import com.qads.qedhex.helpers.CalendarItemAdapter;
import com.qads.qedhex.R;

import java.util.Calendar;

import javax.annotation.Nullable;

public class ItemsFragment extends Fragment {
    private View rootView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference calendarItemRef = db.collection("calendar_slots");
    private CalendarItemAdapter adapter;
    private String start_time;
    private String end_time;
//    private String calID:
//    private String mapID:

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @androidx.annotation.Nullable Bundle savedInstanceState){
        Bundle bundle = this.getArguments();
        rootView = inflater.inflate(R.layout.items_fragment, container, false);
//        calID = bundle.getString("CalID");
//        mapID = bundle.getString("MapID");
        setUpRecyclerView();
        return rootView;
    }

    private void setUpRecyclerView() {
        Query query = calendarItemRef.whereEqualTo("start time", start_time).whereEqualTo("end time", end_time);

        FirestoreRecyclerOptions<CalendarItem> options = new FirestoreRecyclerOptions.Builder<CalendarItem>()
                .setQuery(query, CalendarItem.class).build();

        //adapter = new CalendarItemAdapter(options);
        RecyclerView recyclerView = rootView.findViewById(R.id.items_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

//        adapter.setOnItemClickListener(new CalendarItemAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
//                String id = documentSnapshot.getId();
//                onCategoryClickmeister(id);
//                CalendarItem clicked = (CalendarItem) adapter.getItem(position);
//                Toast.makeText(getContext(), "Position: " + position + " ID: " + clicked.getOptionsList().get(0).get("name"), Toast.LENGTH_SHORT).show(); }
//        });
    }

    public void onCategoryClickmeister(String id){

        Fragment nextFragment = new ItemsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Current CalendarItem", id);
        nextFragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, nextFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        adapter.startListening();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        adapter.stopListening();
//    }
}
