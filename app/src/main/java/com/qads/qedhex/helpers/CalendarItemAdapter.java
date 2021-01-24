package com.qads.qedhex.helpers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.qads.qedhex.R;

import java.util.ArrayList;
import java.util.List;

public class CalendarItemAdapter extends RecyclerView.Adapter<CalendarItemAdapter.MyViewHolder>{

    private ArrayList<InterestsModel> interestsModel;
    private OnNoteListener mOnNoteListener; //this sets the onNoteListener to the viewholder
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    final String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    //this retrieves the entire document with this specific uid
    private DocumentReference docRef = db.collection("users").document(userid);
    List<String> myInterests;

    public CalendarItemAdapter(ArrayList<InterestsModel> data, InterestsAdapter.OnNoteListener onNoteListener) {
        this.interestsModel = data;
        this.mOnNoteListener = (OnNoteListener) onNoteListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return interestsModel.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView interests_tag;
        CardView interests_card;
        InterestsAdapter.OnNoteListener onNoteListener;

        public MyViewHolder(@NonNull View itemView, InterestsAdapter.OnNoteListener onNoteListener) {
            super(itemView);
            this.interests_tag = (TextView) itemView.findViewById(R.id.interests_tag);
            this.interests_card = (CardView) itemView.findViewById(R.id.interests_card);
            this.onNoteListener = onNoteListener;

            //attach onClickListener to entire view
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getAdapterPosition(), interests_card, interests_tag); //getAdapterPosition returns the position of the holder clicked
        }
    }

    public interface OnNoteListener{
        void onNoteClick(int position, CardView cardView, TextView textView); //will send the position of the clicked item
    }
}
