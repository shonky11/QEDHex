package com.qads.qedhex.helpers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.qads.qedhex.R;

public class CalendarItemAdapter extends FirestoreRecyclerAdapter<CalendarItem, CalendarItemAdapter.CalendarItemHolder> {

    private OnItemClickListener listener;

    public CalendarItemAdapter(@NonNull FirestoreRecyclerOptions<CalendarItem> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull CalendarItemHolder holder, int position, @NonNull CalendarItem model) {
        holder.startTime.setText(model.getStartTime());
        holder.endTime.setText(model.getEndTime());
    }



    @NonNull
    @Override
    public CalendarItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_items, parent, false);
        return new CalendarItemHolder(v);

    }

    class CalendarItemHolder extends RecyclerView.ViewHolder {
        TextView startTime, endTime;

        public CalendarItemHolder(@NonNull View itemView) {
            super(itemView);
            startTime = itemView.findViewById(R.id.StartTime);
            endTime = itemView.findViewById(R.id.EndTime);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
