package com.qads.qedhex.helpers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.qads.qedhex.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CalendarItemAdapter extends RecyclerView.Adapter<CalendarItemAdapter.MyViewHolder>{

    private ArrayList<Map<String, Object>> CalModel;
    private OnNoteListener mOnCalListener; //this sets the onNoteListener to the viewholder
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    final String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    //this retrieves the entire document with this specific uid
    private DocumentReference docRef = db.collection("users").document(userid);
    List<String> myInterests;
    private SimpleDateFormat spf = new SimpleDateFormat("HH:mm");


    public CalendarItemAdapter(ArrayList<Map<String, Object>> data, CalendarItemAdapter.OnNoteListener onNoteListener) {
        this.CalModel = data;
        this.mOnCalListener = onNoteListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_items, parent, false);

        //view.setOnClickListener()

        CalendarItemAdapter.MyViewHolder myViewHolder = new MyViewHolder(view, mOnCalListener); //pass in the global onNoteListener
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TextView textView1 = holder.Start;
        TextView textView2 = holder.End;
        Timestamp timestamp1 = (Timestamp) CalModel.get(position).get("start");
        Timestamp timestamp2 = (Timestamp) CalModel.get(position).get("end");

        Date time1 = timestamp1.toDate();
        Date time2 = timestamp2.toDate();

        textView1.setText(spf.format(time1));
        textView2.setText(spf.format(time2));
    }

    @Override
    public int getItemCount() {
        return CalModel.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView Start;
        TextView End;
        CalendarItemAdapter.OnNoteListener onNoteListener;

        public MyViewHolder(@NonNull View itemView, CalendarItemAdapter.OnNoteListener onNoteListener) {
            super(itemView);
            this.Start = (TextView) itemView.findViewById(R.id.StartTime);
            this.End = (TextView) itemView.findViewById(R.id.EndTime);
            this.onNoteListener = onNoteListener;

            //attach onClickListener to entire view
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getAdapterPosition(), Start, End); //getAdapterPosition returns the position of the holder clicked
        }
    }

    public interface OnNoteListener{
        void onNoteClick(int position, TextView textView1, TextView textView2); //will send the position of the clicked item
    }
}
