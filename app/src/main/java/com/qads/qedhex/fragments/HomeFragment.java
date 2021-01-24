package com.qads.qedhex.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.qads.qedhex.R;
import com.qads.qedhex.activities.LoginActivity;
import com.qads.qedhex.activities.SignUpActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
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
                Fragment nextFragment = new MapsFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("Walk Time", walkTime);
                nextFragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, nextFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        signOut(rootView);

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
