package com.treatapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class FragmentMeal extends Fragment {

    private RecyclerView dishList;
    private RecyclerView.Adapter dishAdapter;
    private ArrayList<Dish> dishes;
    int selectedMeal;

    public static FragmentMeal newInstance() {
        FragmentMeal fragment = new FragmentMeal();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meal, container, false);
        //Recycler
        dishList = view.findViewById(R.id.recycler_view);
        dishList.hasFixedSize();
        dishList.setLayoutManager(new LinearLayoutManager((view.getContext())));

        dishAdapter = new DishAdapter(dishes);
        dishList.setAdapter(dishAdapter);

        RecyclerViewMargin decoration = new RecyclerViewMargin(24, 1);
        dishList.addItemDecoration(decoration);
        return view;
    }

    public void setSelectedMeal(int selectedMeal)
    {
        this.selectedMeal = selectedMeal;
    }

    public int getSelectedMeal()
    {
        return selectedMeal;
    }

    public RecyclerView.Adapter getDishAdapter()
    {
        return dishAdapter;
    }

    public void setDishes(ArrayList<Dish> dishes) {
        this.dishes = dishes;
    }

}
