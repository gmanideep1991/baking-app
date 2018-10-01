package com.deepu.android.bakingapp;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.deepu.android.bakingapp.activities.RecipeStepsActivity;
import com.deepu.android.bakingapp.models.Ingredient;
import com.deepu.android.bakingapp.models.Recipe;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
public class RecipeStepsActivityTest {

    @Rule
    public ActivityTestRule<RecipeStepsActivity> mActivityRule = new ActivityTestRule<>(
            RecipeStepsActivity.class,false, false);

    @Test
    public void testOpenIngredients(){
        Intent intent = new Intent();
        Recipe recipe = new Recipe();
        Ingredient ingredientone = new Ingredient();
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(ingredientone);
        recipe.setIngredients( ingredients);
        recipe.setRecipeSteps(new ArrayList<>());
        intent.putExtra ("RECIPE",recipe);
        mActivityRule.launchActivity(intent);

        onView(withId(R.id.show_ingredients)).perform(click());
        onView(withId(R.id.ingredients_recyclerview)).check(matches(isDisplayed()));

        Espresso.pressBack();
        onView(withId(R.id.show_ingredients)).check(matches(isDisplayed()));
    }
}
