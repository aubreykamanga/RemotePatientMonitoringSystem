package com.example.rpms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class Display extends AppCompatActivity {
    /**initialising text view temperature,pulse and PatientName
     *  initialising firebase reference (connection sever/ host firebase)
     * */
    private TextView temp;
    private TextView pulse;
    private TextView Pname;


    private Firebase mRef;
    private Firebase mRef1;

    /**
     * setting XMl IDs to java code
     *   setting connection host firebase
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        getSupportActionBar().hide();
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //
        temp = (TextView)findViewById(R.id.temp);
        pulse = (TextView)findViewById(R.id.pulse);
        Pname = (TextView)findViewById(R.id.P_name);
        String name = getIntent().getStringExtra("name");
        Pname.setText(name);


        mRef = new Firebase("https://remote-patient-monitorin-2df3f-default-rtdb.firebaseio.com/Data/Temperature");
        mRef1 = new Firebase("https://remote-patient-monitorin-2df3f-default-rtdb.firebaseio.com/Data/Heartbeat");


        mRef.addValueEventListener(new ValueEventListener() {
            /**
             * process real-time temperature
             * @param dataSnapshot
             */
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String temperature = dataSnapshot.getValue(String.class);
                temp.setText(temperature);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mRef1.addValueEventListener(new ValueEventListener() {
            /**
             * process real-time Heart rate
             * @param dataSnapshot1
             */
            @Override
            public void onDataChange(DataSnapshot dataSnapshot1) {
                String pulserate = dataSnapshot1.getValue(String.class);
                pulse.setText(pulserate);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }



}