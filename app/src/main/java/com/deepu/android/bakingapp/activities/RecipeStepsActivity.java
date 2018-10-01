package com.deepu.android.bakingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.deepu.android.bakingapp.R;
import com.deepu.android.bakingapp.fragments.RecipeIngredientsFragment;
import com.deepu.android.bakingapp.fragments.RecipeStepDetailsFragment;
import com.deepu.android.bakingapp.fragments.RecipeStepsFragment;
import com.deepu.android.bakingapp.models.Ingredient;
import com.deepu.android.bakingapp.models.Recipe;
import com.deepu.android.bakingapp.models.RecipeStep;

import java.util.ArrayList;
import java.util.List;

import static com.deepu.android.bakingapp.utilities.NetworkUtils.isInternetAvailable;

public class RecipeStepsActivity extends AppCompatActivity implements RecipeStepsFragment.OnStepClickListener, RecipeStepsFragment.OnIngredientsClickListener {

    boolean isTwoPane;
    private List<RecipeStep> recipeSteps;
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);
        isTwoPane = getResources().getBoolean(R.bool.twoPaneMode);
        Intent intent = getIntent();
        recipe = intent.getParcelableExtra(getString(R.string.recipe));
        recipeSteps = recipe.getRecipeSteps();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(getString(R.string.recipe_steps), (ArrayList<RecipeStep>) recipeSteps);
        if (savedInstanceState == null) {
            RecipeStepsFragment recipeStepsFragment = new RecipeStepsFragment();
            recipeStepsFragment.setArguments(bundle);
            recipeStepsFragment.setRetainInstance(true);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.recipe_steps_fragment, recipeStepsFragment).commit();
        }
    }

    @Override
    public void onStepSelected(int position) {
        if (isInternetAvailable(this)) {
            RecipeStep recipeStep = recipeSteps.get(position);
            Bundle bundle = new Bundle();
            bundle.putParcelable(getString(R.string.recipe_step_detail), recipeStep);
            RecipeStepDetailsFragment recipeStepDetailsFragment = new RecipeStepDetailsFragment();
            recipeStepDetailsFragment.setArguments(bundle);
            recipeStepDetailsFragment.setRetainInstance(true);
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (isTwoPane) {
                fragmentManager.beginTransaction().replace(R.id.recipe_details_fragment, recipeStepDetailsFragment).commit();
            } else {
                fragmentManager.beginTransaction().replace(R.id.recipe_steps_fragment, recipeStepDetailsFragment).addToBackStack(null).commit();
            }
        } else {
            Toast.makeText(this, getString(R.string.network_unavailable), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onIngredientsClicked() {
        if (isInternetAvailable(this)) {
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(getString(R.string.recipe_ingredients), (ArrayList<Ingredient>) recipe.getIngredients());
            RecipeIngredientsFragment ingredientsFragment = new RecipeIngredientsFragment();
            ingredientsFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.recipe_steps_fragment, ingredientsFragment).addToBackStack(null).commit();

        } else {
            Toast.makeText(this, getString(R.string.network_unavailable), Toast.LENGTH_LONG).show();
        }
    }
}
