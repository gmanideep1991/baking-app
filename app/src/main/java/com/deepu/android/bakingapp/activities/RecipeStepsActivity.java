package com.deepu.android.bakingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.deepu.android.bakingapp.R;
import com.deepu.android.bakingapp.fragments.RecipeStepDetailsFragment;
import com.deepu.android.bakingapp.fragments.RecipeStepsFragment;
import com.deepu.android.bakingapp.models.Recipe;
import com.deepu.android.bakingapp.models.RecipeStep;

public class RecipeStepsActivity extends AppCompatActivity implements RecipeStepsFragment.OnStepClickListener {

    public static final String STEP_RECIPE = "STEP_RECIPE";
    private Recipe recipe;
    boolean isTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);
        isTwoPane = getResources().getBoolean(R.bool.twoPaneMode);
        Intent intent = getIntent();
        recipe = intent.getParcelableExtra(getString(R.string.recipe));
        Bundle bundle = new Bundle();
        bundle.putParcelable( STEP_RECIPE,recipe);
        if(savedInstanceState == null){
            RecipeStepsFragment recipeStepsFragment= new RecipeStepsFragment();
            recipeStepsFragment.setArguments(bundle);
            recipeStepsFragment.setRetainInstance(true);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.recipe_steps_fragment,recipeStepsFragment).commit();
        }

    }

    @Override
    public void onStepSelected(int position) {
        RecipeStep recipeStep = recipe.getRecipeSteps().get(position);
        Bundle bundle = new Bundle();
        bundle.putParcelable( getString(R.string.description),recipeStep);
        RecipeStepDetailsFragment recipeStepDetailsFragment= new RecipeStepDetailsFragment();
        recipeStepDetailsFragment.setArguments(bundle);
        recipeStepDetailsFragment.setRetainInstance(true);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(isTwoPane){
            fragmentManager.beginTransaction().replace(R.id.recipe_details_fragment,recipeStepDetailsFragment).commit();
        }
        else{
            fragmentManager.beginTransaction().replace(R.id.recipe_steps_fragment,recipeStepDetailsFragment).addToBackStack(null).commit();
        }
    }
}
