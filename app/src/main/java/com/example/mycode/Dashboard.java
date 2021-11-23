package com.example.mycode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Dashboard extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase ;
    myadapter adpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));


        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("languages");

        FirebaseRecyclerOptions<model_class> options =
                new FirebaseRecyclerOptions.Builder<model_class>()
                        .setQuery(query, model_class.class)
                        .build();

        adpter = new myadapter(options);
        recyclerView.setAdapter(adpter);

    }


    @Override
    protected void onStart() {
        super.onStart();
        adpter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adpter.stopListening();
    }
}