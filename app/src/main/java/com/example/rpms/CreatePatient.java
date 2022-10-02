package com.example.rpms;

import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.EditText;

import android.widget.Toast;



import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

/**
 * The type Create patient.
 */
public class  CreatePatient extends AppCompatActivity {
    /**Initialising edit texts*/
    private EditText editTextName;
    private EditText editTextAge;
    private EditText editTextSex;


    /**
     * Setting the close button for canceling the New patient creation
     * Assignment of the parameters
     */
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_patient);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

//        spinner Gender;
//        Spinner spinner= findViewById( R.id.spinner);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Gender, android.R.layout.simple_spinner_item);adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);


     //Setting the close button for canceling the New patient creation
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);
        setTitle("Add patient");
    // Assignment of the parameters
        editTextName = findViewById(R.id.edit_name);
        editTextAge = findViewById(R.id.edit_age);
        editTextSex = findViewById(R.id.edit_Sex);
    }

    /**
     * Telling the system to use the new note menu, as the menu for this activity
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.new_note, menu);
        return super.onCreateOptionsMenu(menu);


    }

    /**
     * Telling the system what we want to view when we click
     * @param item
     * @return
     */
    @Override

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save_note) {
            saveNote();
            return true;

        }

        return super.onOptionsItemSelected(item);

    }

    /**
     * create save note method,get data from editText, create new document with it and save it in Firestore
     */
    private void saveNote(){
        String name = editTextName.getText().toString();
        int age = Integer.parseInt(editTextAge.getText().toString().trim());
        String sex = editTextSex.getText().toString();

           //checking if the Name and Sex is empty. To avoid saving empty string
        if (name.trim().isEmpty() || sex.trim().isEmpty()){
            Toast.makeText(this, "Insert name and sex",Toast.LENGTH_SHORT).show();
            return;
        }
        //saving note in Firestore
        CollectionReference notebookRef = FirebaseFirestore.getInstance()
                .collection("Notebook");
        notebookRef.add(new Note(name,age,sex));
        Toast.makeText(this,"Patient added", Toast.LENGTH_SHORT).show();
        finish();
    }




}