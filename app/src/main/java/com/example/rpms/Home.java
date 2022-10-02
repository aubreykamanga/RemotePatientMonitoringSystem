package com.example.rpms;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Home extends AppCompatActivity implements NoteAdapter.OnItemClickListener{
    /**Reference to the fireStore database
     * Reference to the Notebook Collection
     * */
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();


    private final CollectionReference notebookRef = db.collection("Notebook");

    private  NoteAdapter adapter;
    private FirebaseAuth firebaseAuth;

    /**
     *  connect recyclerView to the Adapter
     *  Creating floating action button for adding New patients and Setting onClickListener
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        firebaseAuth = FirebaseAuth.getInstance();

        FloatingActionButton btnAddNote = findViewById(R.id.buttom_add_note);
        btnAddNote.setOnClickListener(new View.OnClickListener() {
            /**
             * starting new activity createPatient after clicking
             * @param v
             */
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this,CreatePatient.class));
            }
        });

        setUpRecyclerView();
    }

    /**
     *  Setting swipe direction to delete Documents
     */
    private void setUpRecyclerView() {
        Query query = notebookRef;

        FirestoreRecyclerOptions<Note> options = new FirestoreRecyclerOptions.Builder<Note>()
                .setQuery(query, Note.class)
                .build();
        adapter = new NoteAdapter(options, this);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.deleteItem(viewHolder.getAdapterPosition());

            }
        }).attachToRecyclerView(recyclerView);

//        adapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
//                Note note = documentSnapshot.toObject(Note.class);
//                String id = documentSnapshot.getId();
//                String path = documentSnapshot.getReference().getPath();
//                Toast.makeText(Home.this,
//                        "position: " + position + " ID: " + id,  Toast.LENGTH_SHORT).show();
//                Intent i = new Intent(Home.this,Display.class);
//
//
//                startActivity();
//            }
//        });
    }

    /**
     *  Telling adapter when to start Listening for database changes
     */
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    /**
     * Telling adapter when to stop Listening for database changes
     */
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    /**
     *  Logging out of the system
     * @param item
     * @return
     */
    @Override

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.logout:
               firebaseAuth.signOut();
                finish();
                startActivity(new Intent(Home.this,MainActivity.class));
        }

        return super.onOptionsItemSelected(item);

}}