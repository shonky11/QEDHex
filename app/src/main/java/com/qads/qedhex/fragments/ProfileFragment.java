package com.qads.qedhex.fragments;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.qads.qedhex.R;
import com.qads.qedhex.helpers.InterestsAdapter;
import com.qads.qedhex.helpers.InterestsModel;
import com.qads.qedhex.helpers.MyInterestsAdapter;
import com.qads.qedhex.helpers.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    private TextView username, userCrsid, userBio, userCollege, userYear, userGradYear, userInterests, admin;
    private TextView signout_btn, noInterests;
    private CircleImageView profilePic;
    private TextView edit_btn;
    private GoogleSignInClient mSignInClient;
    private ImageView add_interests_btn, done_interests_btn;
    private ListenerRegistration Regiboi;
    private FirebaseFirestore mFirebaseFirestore = FirebaseFirestore.getInstance();
    private String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private DocumentReference userDocRef = mFirebaseFirestore.collection("users").document(userID);

    //Firebase Instance variables - add them when you need them and explain their function

    //you can get an instance of the authentication by doing .getInstance()
    private FirebaseAuth mFirebaseAuth;
    //this will get the current instance of the document in cloud firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    final String userid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    //this retrieves the entire document with this specific uid
    private DocumentReference docRef = db.collection("users").document(userid);

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;
    List<String> myInterests;
    ArrayList<String> mInterests;

    public static User currentUser;


    //GridLayout stuff
    private GridLayoutManager gridLayoutManager, all_gridLayoutManager;
    private RecyclerView.Adapter recyclerAdapter, all_recyclerAdapter;
    private RecyclerView recyclerView, all_recyclerView;
    private ArrayList<InterestsModel> interestsModel;
    private ArrayList<InterestsModel> myInterestsList;
    private ListenerRegistration userReg;
    private MaterialCalendarView userCalendar;

    private String TAG = "ProfileFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.profile_fragment, container, false);

        final TextView username = (TextView) v.findViewById(R.id.user_name);
        final TextView userAge = (TextView) v.findViewById(R.id.age);
        final TextView userID = (TextView) v.findViewById(R.id.id);


        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error == null){
                    if (value.exists()){
                        String first_name = value.getString("firstname");
                        String last_name = value.getString("lastname");
                        String uid = value.getString("uid");
                        String age2 = value.getString("age");

                        username.setText(first_name + last_name);
                        userID.setText(uid);
                        userAge.setText(age2);

                    }

                }


            }
        });


        return v;
    }

}
