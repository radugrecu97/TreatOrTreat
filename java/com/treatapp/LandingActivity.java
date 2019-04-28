package com.treatapp;

import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LandingActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Fragment selectedFragmentMeal = null;
    String meal = "";
    boolean loaded = false;
    Toolbar myToolbar;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ArrayList<Dish> dishesBreakfast, dishesLunch, dishesDinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("dishes");

        //Appbar
        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        //Drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, myToolbar, R.string.open, R.string.close
        );

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_drawer);
        navigationView.setNavigationItemSelectedListener(this);

        View navHeaderView = navigationView.inflateHeaderView(R.layout.drawer_header);
        TextView headerTitle = (TextView) navHeaderView.findViewById(R.id.nav_header_title);

        dishesBreakfast = new ArrayList<Dish>();
        dishesLunch = new ArrayList<Dish>();
        dishesDinner = new ArrayList<Dish>();

        //Database read
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!loaded) {


                    if (dataSnapshot.exists()) {
                        for (DataSnapshot ds : dataSnapshot.child("breakfast").getChildren()) {
                            Dish newDish = new Dish("", "");
                            newDish.setName(ds.getValue(Dish.class).getName());
                            newDish.setDesc(ds.getValue(Dish.class).getDesc());
                            dishesBreakfast.add(newDish);
                        }

                        for (DataSnapshot ds : dataSnapshot.child("lunch").getChildren()) {
                            Dish newDish = new Dish("", "");
                            newDish.setName(ds.getValue(Dish.class).getName());
                            newDish.setDesc(ds.getValue(Dish.class).getDesc());
                            dishesLunch.add(newDish);
                        }

                        for (DataSnapshot ds : dataSnapshot.child("dishes").getChildren()) {
                            Dish newDish = new Dish("", "");
                            newDish.setName(ds.getValue(Dish.class).getName());
                            newDish.setDesc(ds.getValue(Dish.class).getDesc());
                            dishesDinner.add(newDish);
                        }
                        loaded = true;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });





        //Bottom navigation
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.nav_bottom);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.bottom_nav_breakfast:
                                selectedFragmentMeal = FragmentMeal.newInstance();
                                ((FragmentMeal) selectedFragmentMeal).setSelectedMeal(0);
                                ((FragmentMeal) selectedFragmentMeal).setDishes(dishesBreakfast);
                                myToolbar.setTitle(R.string.name_breakfast);
                                meal = "breakfast";
                                break;
                            case R.id.bottom_nav_lunch:
                                selectedFragmentMeal = FragmentMeal.newInstance();
                                ((FragmentMeal) selectedFragmentMeal).setSelectedMeal(1);
                                ((FragmentMeal) selectedFragmentMeal).setDishes(dishesLunch);
                                myToolbar.setTitle(R.string.name_lunch);
                                meal = "lunch";
                                break;
                            case R.id.bottom_nav_dinner:
                                selectedFragmentMeal = FragmentMeal.newInstance();
                                ((FragmentMeal) selectedFragmentMeal).setSelectedMeal(2);
                                ((FragmentMeal) selectedFragmentMeal).setDishes(dishesDinner);
                                myToolbar.setTitle(R.string.name_dinner);
                                meal = "dinner";
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragmentMeal);
                        transaction.commit();
                        return true;

                    }
                });

        //bottomNavigationView.setSelectedItemId(R.id.bottom_nav_breakfast);

//        //Manually displaying the first fragment - one time only
//        selectedFragmentMeal = FragmentMeal.newInstance();
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.frame_layout, selectedFragmentMeal);
//        ((FragmentMeal) selectedFragmentMeal).setSelectedMeal(0);
//        transaction.commit();


    }

    //Appbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {

            case R.id.action_settings:
                Toast.makeText(LandingActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Drawer
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        // blah blah
        return true;
    }

    //Floating Action Button
    public void onClickFab(View view) {
        Dish dish = new Dish("New Dish", "New Description");
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("dishes");

        DatabaseReference newRef = myRef.child(meal).push();
        newRef.setValue(dish);
        ((DishAdapter )((FragmentMeal) selectedFragmentMeal).getDishAdapter()).addDish("New Dish", "Description");
    }
}
