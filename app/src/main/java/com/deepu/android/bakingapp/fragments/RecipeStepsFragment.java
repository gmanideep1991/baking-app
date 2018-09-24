package com.deepu.android.bakingapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deepu.android.bakingapp.R;
import com.deepu.android.bakingapp.activities.RecipeStepsActivity;
import com.deepu.android.bakingapp.adapters.RecipeStepsAdapter;
import com.deepu.android.bakingapp.models.Recipe;
import com.deepu.android.bakingapp.utilities.IngredientUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepsFragment extends Fragment {

    @BindView(R.id.recipe_steps_recyclerView)
    RecyclerView recipe_steps;
    @BindView(R.id.ingredientsText)
    TextView ingredients;

    private Recipe recipe;
    //LinearLayoutManager recipeStepsManager = new LinearLayoutManager(getContext());

    OnStepClickListener callback;
    public RecipeStepsFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_steps,container,false);
        ButterKnife.bind(this,rootView);
        populateRecipeSteps();
        ingredients.setText(IngredientUtils.convertToString(recipe.getIngredients()));
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            recipe = bundle.getParcelable(RecipeStepsActivity.STEP_RECIPE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            callback = (OnStepClickListener) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException(context.toString()+ "must implement onStepClickListener");
        }
    }

    private void populateRecipeSteps(){
        RecipeStepsAdapter recipeStepsAdapter = new RecipeStepsAdapter(recipe.getRecipeSteps(),callback);
        recipe_steps.setAdapter(recipeStepsAdapter);
        recipe_steps.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    public interface OnStepClickListener{
        void onStepSelected(int position);
    }
}
