package com.deepu.android.bakingapp.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.deepu.android.bakingapp.R;
import com.deepu.android.bakingapp.fragments.RecipeListFragment;

import static com.deepu.android.bakingapp.utilities.NetworkUtils.isInternetAvailable;

public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(isInternetAvailable(this)){
            RecipeListFragment recipeList = new RecipeListFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.recipe_card,recipeList).commit();
        }
        else {
            Toast.makeText(this,getString(R.string.network_unavailable),Toast.LENGTH_LONG).show();
        }
    }
}
