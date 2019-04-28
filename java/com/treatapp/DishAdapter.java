package com.treatapp;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.ViewHolder> {

    private ArrayList<Dish> dishes;
    private Activity activity;;

    DishAdapter(ArrayList<Dish> items)
    {
        dishes = items;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        // each data item is just a string in this case
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name = itemView.findViewById(R.id.textTitle);
        }

        @Override
        public void onClick(View view)
        {
            // get the position on recyclerview.
            int pos = getLayoutPosition();
            Intent intent = new Intent(view.getContext(), DishActivity.class);
            intent.putExtra("selectedMeal", pos);
            getActivity(view).startActivity(intent);
        }

        private Activity getActivity(View view) {
            Context context = view.getContext();
            while (context instanceof ContextWrapper) {
                if (context instanceof Activity) {
                    return (Activity)context;
                }
                context = ((ContextWrapper)context).getBaseContext();
            }
            return null;
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(ViewHolder viewHolder, int position)
    {
        viewHolder.name.setText(dishes.get(position).getName());
    }

    public int getItemCount()
    {
        return dishes.size();
    }

    public void addDish(String name, String desc)
    {
        dishes.add(new Dish(name, desc));
        notifyDataSetChanged();
    }


}