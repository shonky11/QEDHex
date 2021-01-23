package com.qads.qedhex;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoginActivity extends AppCompatActivity implements InterestsAdapter.OnNoteListener{

    EditText txtfirstname, txtlastname, txtage, txtinterests;
    TextView privacyPolicy;
    private TextView noInterests;
    CardView signupbtn;
    Spinner txtcollegeid, txtdegreeid;
    CircleImageView profilePic;
    CheckBox checkBox;
    private ImageView add_interests_btn, done_interests_btn;

    String personName, personGivenName, personFamilyName, personEmail, personId;

    private static final int PICK_IMAGE_REQUEST =  1; //this is for the picture file intent in openFileChooser

    private Uri imageUri; //this is a uri (similar to url) that points to the image and uploads it to the firebase storage
    //for uploading profilepic to firebase
    private StorageReference storageRef;
    private DatabaseReference firebaseDatabaseReference;
    private StorageTask uploadTask;

    // Firebase instance variable
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseDatabase mFirebaseDatabase;
    //private DatabaseReference mFirebaseDatabaseReference;

    //private FirebaseFirestore db;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    StorageReference photoReference= storageReference.child("users/DefaultProfilePic/ic_profile_icon_24dp.xml");
    private String defaultProfilePic = "users/DefaultProfilePic/ic_profile_icon_24dp.xml";

    //this will get the current instance of the document in cloud firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    final String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    //this retrieves the entire document with this specific uid - needed to update the profile information
    private DocumentReference docRef = db.collection("users").document(userid);

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;
    List<String> myInterests;
    ArrayList<String> mInterests;


    //GridLayout stuff
    private GridLayoutManager gridLayoutManager, all_gridLayoutManager;
    private RecyclerView.Adapter recyclerAdapter, all_recyclerAdapter;
    private RecyclerView recyclerView, all_recyclerView;
    private ArrayList<InterestsModel> interestsModel;
    private ArrayList<InterestsModel> myInterestsList;

    private String TAG = "MyActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        checkBox = findViewById(R.id.checkbox);

        storageRef = FirebaseStorage.getInstance().getReference("users/" + userid);

        txtfirstname = (EditText) findViewById(R.id.first);
        txtlastname = (EditText) findViewById(R.id.last);
        txtage = (EditText) findViewById(R.id.age);
        //txtinterests = (EditText) findViewById(R.id.interests);
        //txtdegree = (EditText) findViewById(R.id.degree);
        signupbtn = (CardView) findViewById(R.id.signupbtn);
        profilePic = findViewById(R.id.original_profile_image);

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //when the image is pressed, an intent will be sent to open the images file for user to pick a new picture which will show on the page
                openFileChooser();
                uploadFile();
            }
        });

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Logic

                //convert all textViews to string
                String first_name = txtfirstname.getText().toString().trim();
                String last_name = txtlastname.getText().toString().trim();
                //String college_id = txtcollegeid.getText().toString().trim();
                String age = txtage.getText().toString().trim();
                //String interests1 = txtinterests.getText().toString().trim();
                //String degree_id = txtdegree.getText().toString().trim();

                if(first_name.isEmpty() || last_name.isEmpty() || age.isEmpty() || !checkBox.isChecked()){
                    Toast.makeText(getApplicationContext(), "Please fill out all the fields", Toast.LENGTH_SHORT).show();
                }else if (!checkBox.isChecked()){
                    Toast.makeText(getApplicationContext(), "Please check the tick box", Toast.LENGTH_SHORT).show();
                }
                else{
                    User users = new User(first_name, last_name, age, mFirebaseAuth.getUid(), null);
                    docRef.set(users)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "DocumentSnapshot written with ID: ");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding document", e);
                                }
                            });

                    uploadFile();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }

            }
        });

        //gets current instance of the database
        mFirebaseAuth = FirebaseAuth.getInstance();


        noInterests = (TextView) findViewById(R.id.no_interests);

        recyclerView = (RecyclerView) findViewById(R.id.interests_recycler);
        recyclerView.setHasFixedSize(true);

        all_recyclerView = (RecyclerView) findViewById(R.id.all_interests_recycler);

        gridLayoutManager = new GridLayoutManager(this, 2);
        all_gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        all_recyclerView.setLayoutManager(all_gridLayoutManager);
        all_recyclerView.setItemAnimator(new DefaultItemAnimator());

        interestsModel = new ArrayList<InterestsModel>();
        myInterestsList = new ArrayList<InterestsModel>(); //creates a list of all the interests

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    assert document != null;
                    if (document.exists()) {
                        myInterests = (List<String>) document.get("interests");
                        //final ArrayList<String> list = new ArrayList<String>();
                        if (myInterests == null||myInterests.isEmpty()){
                            //interestsModel.add(new InterestsModel("You don't have any interests"));
                            noInterests.setVisibility(View.VISIBLE);
                        }else{
                            for (int i = 0; i < myInterests.size(); ++i) {
                                noInterests.setVisibility(View.GONE);
                                interestsModel.add(new InterestsModel(myInterests.get(i)));
                                myInterestsList.add(new InterestsModel(myInterests.get(i)));
                            }
                        }

                        //GridAdapter gridAdapter = new GridAdapter(getContext(), list);
                        //gridView.setAdapter(gridAdapter);

                        /*//this is for the list view
                        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, list);
                        myInterestsList.setAdapter(adapter);
                        setListViewHeight(myInterestsList);*/

                        initMyRecyclerView();

                        Log.d(TAG, interestsModel.toString());
                    }
                } else{
                    Toast.makeText(getApplicationContext(), "didn't work", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //inflate new recyclerview on add buttons press
        add_interests_btn = (ImageView) findViewById(R.id.add_interests_button);
        done_interests_btn = (ImageView) findViewById(R.id.done_interests_button);

        add_interests_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interestsModel.clear();
                for (int i = 0; i < DataSet.all_interests.size(); i++){
                    interestsModel.add(new InterestsModel(
                            DataSet.all_interests.get(i)
                    ));
                }


                initAllRecyclerView();
                noInterests.setVisibility(View.GONE);
                done_interests_btn.setVisibility(View.VISIBLE);
                add_interests_btn.setVisibility(View.INVISIBLE);

            }
        });

        done_interests_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                myInterests = (List<String>) document.get("interests");
                                interestsModel.clear();
                                //final ArrayList<String> list = new ArrayList<String>();
                                if (myInterests == null||myInterests.isEmpty()){
                                    //interestsModel.add(new InterestsModel("You don't have any interests"));
                                    noInterests.setVisibility(View.VISIBLE);
                                }else{
                                    for (int i = 0; i < myInterests.size(); ++i) {
                                        noInterests.setVisibility(View.GONE);
                                        interestsModel.add(new InterestsModel(myInterests.get(i)));
                                    }
                                }

                                //GridAdapter gridAdapter = new GridAdapter(getContext(), list);
                                //gridView.setAdapter(gridAdapter);

                                /*//this is for the list view
                                final ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, list);
                                myInterestsList.setAdapter(adapter);
                                setListViewHeight(myInterestsList);*/

                                initMyRecyclerView();
                                done_interests_btn.setVisibility(View.INVISIBLE);
                                add_interests_btn.setVisibility(View.VISIBLE);
                                Log.d(TAG, interestsModel.toString());
                            }
                        } else{
                            Toast.makeText(getApplicationContext(), "didn't work", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    public void initAllRecyclerView(){
        all_recyclerAdapter = new InterestsAdapter(interestsModel, this);
        all_recyclerView.setAdapter(all_recyclerAdapter);
        all_recyclerAdapter.notifyDataSetChanged();
        all_recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    public void initMyRecyclerView(){
        recyclerAdapter = new MyInterestsAdapter(interestsModel);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.notifyDataSetChanged();
        recyclerView.setVisibility(View.VISIBLE);
        all_recyclerView.setVisibility(View.GONE);
    }


    @Override
    public void onNoteClick(int position, CardView cardView, TextView textView) {
        //here do the things you want to do on a click event
        //e.g create an intent to go somewhere
        String one_interest = interestsModel.get(position).getInterests(); //this gets a reference to the object that is pressed
        Log.d(TAG, "onNoteClick: " + String.valueOf(one_interest));
        if (cardView.getCardBackgroundColor() == ColorStateList.valueOf(getResources().getColor(R.color.colorAccent))){
            cardView.setCardBackgroundColor(Color.WHITE);
            textView.setTextColor(Color.BLACK);
            docRef.update("interests", FieldValue.arrayRemove(one_interest));
        }
        else{
            cardView.setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
            textView.setTextColor(Color.WHITE);
            docRef.update("interests", FieldValue.arrayUnion(one_interest));
        }
        //cardView.setCardBackgroundColor(Color.BLACK);
    }


    public static class DataSet {

        static ArrayList<String> all_interests = new ArrayList<>(Arrays.asList(
                "Dogs",
                "Cats",
                "Beaney",
                "Mercers",
                "Engineering",
                "Films",
                "Netflix",
                "Lakes",
                "Trees",
                "COVID"
        ));

    }

    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*"); //ensures we only see images in our file chooser
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null & data.getData() != null){
            imageUri = data.getData();

            //IF WE WANTED TO LOAD THE PICTURE FROM THE FILE INTO THE IMAGE VIEW WE WOULD DO THIS
            //native way of putting in an image into an imageview
            //mProfilePic.setImageURI(imageUri);

            //using picasso
            Picasso.get().load(imageUri).into(profilePic);
        }
    }

    //this will upload the picture file selected to firebase storage
    private void uploadFile(){
        if (imageUri != null){
            //set the picture name to profilePic
            StorageReference fileReference = storageRef.child("profilePic");//+ getFileExtension(imageUri)

            //this puts the file into storage
            uploadTask = fileReference.putFile(imageUri)
                    //then we need to try add the url to the database
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getApplicationContext(), "Upload successful", Toast.LENGTH_SHORT).show();
                            if (taskSnapshot.getMetadata()!= null){
                                if (taskSnapshot.getMetadata().getReference()!=null){
                                    String result = taskSnapshot.getStorage().getPath();
                                    //updates the database with this --> THIS MIGHT NOT BE NEEDED
                                    docRef.update("profilePicRef", result);
                                }
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }else{
            //Toast.makeText(ProfileEditActivity.this, "No File Selected", Toast.LENGTH_SHORT).show();
        }
    }
}
