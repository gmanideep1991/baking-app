package com.deepu.android.bakingapp.adapters;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.deepu.android.bakingapp.IngredientWidgetProvider;
import com.deepu.android.bakingapp.R;
import com.deepu.android.bakingapp.activities.RecipeStepsActivity;
import com.deepu.android.bakingapp.models.Recipe;
import com.deepu.android.bakingapp.utilities.IngredientUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {

    private List<Recipe> recipes;
    private Context context;

    public RecipeListAdapter(List<Recipe> recipes,Context context){
        this.recipes = recipes;
        this.context = context;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_card,parent,false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.bindTo(recipes.get(position));
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.recipe_name)
        TextView recipe_name;
        @BindView(R.id.recipe_servings)
        TextView recipe_servings;

        private Recipe recipe;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    launchRecipeSteps(recipe);
                }
            });
        }

        public void bindTo(@NonNull Recipe recipe){
            recipe_name.setText(recipe.getName());
            recipe_servings.setText("Servings : " +String.valueOf(recipe.getServings()));
            this.recipe = recipe;
        }


    }

    private void launchRecipeSteps(Recipe recipe){
        Intent intent = new Intent(context, RecipeStepsActivity.class);
        intent.putExtra(context.getResources().getString(R.string.recipe),recipe);
        context.startActivity(intent);
        updateWidget(IngredientUtils.convertToString(recipe.getIngredients()));
    }

    private void updateWidget(String ingredients){

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget_provider);
        ComponentName thisWidget = new ComponentName(context, IngredientWidgetProvider.class);

        remoteViews.setTextViewText(R.id.appwidget_text, ingredients);
        appWidgetManager.updateAppWidget(thisWidget, remoteViews);
    }
}
