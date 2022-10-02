package com.example.rpms;



import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

/**Adapter gets the data from data zones into recyclerView*/
public class NoteAdapter extends FirestoreRecyclerAdapter<Note, NoteAdapter.NoteHolder> {

    private OnItemClickListener listener;
    private Context context;

    public NoteAdapter(@NonNull FirestoreRecyclerOptions<Note> options,  Context context) {
        super(options);
        this.context = context;
    }

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public NoteAdapter(@NonNull FirestoreRecyclerOptions<Note> options) {
        super(options);
    }

    /**
     *  Telling adapter what we want to put in each View in our Card layout
     * @param noteHolder
     * @param i
     * @param note
     */
    @Override
    protected void onBindViewHolder(@NonNull NoteHolder noteHolder, int i, @NonNull Note note) {
        noteHolder.textViewName.setText(note.getName());
        noteHolder.textViewAge.setText(String.valueOf(note.getAge()));
        noteHolder.textViewSex.setText(note.getSex());

        noteHolder.baseView.setOnClickListener(v->{
            Intent intent = new Intent(context, Display.class);
            intent.putExtra("name", note.getName());
            context.startActivity(intent);
        });

    }

    /**
     * Telling Adapter which layout to inflate
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item,
                parent, false);
        return new NoteHolder(view);
    }
    /**Deleting the document in the Firestore and update the recyclerView*/
    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }
/**creating inner class , for ViewHolder
 *
 * */
    class NoteHolder extends RecyclerView.ViewHolder{
        /**Declaring all the views available*/
        TextView textViewName;
        TextView textViewAge;
        TextView textViewSex;
        CardView baseView;

        /**constructor of the viewHolder
         * Setting onClickListener on the card in recyclerView
         * */
        public NoteHolder(View itemView){
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewAge = itemView.findViewById(R.id.age);
            textViewSex = itemView.findViewById(R.id.sex);
            baseView = itemView.findViewById(R.id.base_view);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null){
                    listener.onItemClick(getSnapshots().getSnapshot(position), position);
                }
            });
        }
    }
    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);

    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;


    }
}
