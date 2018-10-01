package com.deepu.android.bakingapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deepu.android.bakingapp.R;
import com.deepu.android.bakingapp.adapters.RecipeStepsAdapter;
import com.deepu.android.bakingapp.models.RecipeStep;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepsFragment extends Fragment {

    @BindView(R.id.recipe_steps_recyclerView)
    RecyclerView recipe_steps;

    @BindView(R.id.show_ingredients)
    CardView showIngredients;

    private List<RecipeStep> recipeSteps;

    OnStepClickListener callback;
    OnIngredientsClickListener ingredientsClickListener;
    public RecipeStepsFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_steps,container,false);
        ButterKnife.bind(this,rootView);
        populateRecipeSteps();
        showIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ingredientsClickListener.onIngredientsClicked();
            }
        });
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            recipeSteps = bundle.getParcelableArrayList(getString(R.string.recipe_steps));
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            callback = (OnStepClickListener) context;
            ingredientsClickListener = (OnIngredientsClickListener) context;

        }
        catch (ClassCastException e){
            throw new ClassCastException(context.toString()+ "must implement Listeners");
        }
    }

    private void populateRecipeSteps(){
        RecipeStepsAdapter recipeStepsAdapter = new RecipeStepsAdapter(recipeSteps,callback);
        recipe_steps.setAdapter(recipeStepsAdapter);
        recipe_steps.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    public interface OnStepClickListener{
        void onStepSelected(int position);
    }

    public interface OnIngredientsClickListener{
        void onIngredientsClicked();
    }
}
