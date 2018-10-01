package com.deepu.android.bakingapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deepu.android.bakingapp.R;
import com.deepu.android.bakingapp.adapters.IngredientsListAdapter;
import com.deepu.android.bakingapp.models.Ingredient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeIngredientsFragment extends Fragment {

    List<Ingredient> ingredients;

    @BindView(R.id.ingredients_recyclerview)
    RecyclerView ingredientsView;

    public RecipeIngredientsFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_ingredients,container,false);
        ButterKnife.bind(this,rootView);
        populateIngredients();
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            ingredients = bundle.getParcelableArrayList(getString(R.string.recipe_ingredients));
        }
    }

    private void populateIngredients(){
        IngredientsListAdapter ingredientsAdapter = new IngredientsListAdapter(ingredients);
        ingredientsView.setAdapter(ingredientsAdapter);
        ingredientsView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
