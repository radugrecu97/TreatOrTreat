package com.treatapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DishActivity extends AppCompatActivity {

    EditText textName, textDesc;
    Boolean enabled;
    // Write a message to the database
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish);
        textName = findViewById(R.id.textName);
        textDesc = findViewById(R.id.textDesc);
        textName.setEnabled(false);
        textDesc.setEnabled(false);
        enabled = false;
        //prevent keyboard from popping up
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("dishes");

    }

    //Floating Action Button
    public void onClickFab(View view)
    {
        if (enabled)
        {
            //disable editing
            textName.setEnabled(false);
            textDesc.setEnabled(false);

            //writeNewDish(textName.getText().toString(), textDesc.getText().toString());
            enabled = false;
        }
        else
        {
            //enable editing
            textName.setEnabled(true);
            textDesc.setEnabled(true);
            enabled = true;
        }
        textDesc.scrollTo(0,0);
    }

    private void writeNewDish(String name, String desc)
    {
        Dish dish = new Dish(name, desc);
        myRef.setValue(dish);
    }
}
