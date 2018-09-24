package com.deepu.android.bakingapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deepu.android.bakingapp.R;
import com.deepu.android.bakingapp.fragments.RecipeStepsFragment;
import com.deepu.android.bakingapp.models.RecipeStep;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.RecipeStepsViewHolder> {

    private List<RecipeStep> recipeSteps;
    RecipeStepsFragment.OnStepClickListener callback;

    public RecipeStepsAdapter(List<RecipeStep> recipeSteps, RecipeStepsFragment.OnStepClickListener callback){
        this.recipeSteps = recipeSteps;
        this.callback = callback;
    }

    @NonNull
    @Override
    public RecipeStepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_step_card,parent,false);
        return new RecipeStepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeStepsViewHolder holder, int position) {
        holder.bindTo(recipeSteps.get(position),position);
    }

    @Override
    public int getItemCount() {
        return recipeSteps.size();
    }

    class RecipeStepsViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.recipe_step)
        TextView recipe_step;

        private RecipeStep recipeStep;
        private int position;

        public RecipeStepsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    callback.onStepSelected(position);
                }
            });
        }

        public void bindTo(@NonNull RecipeStep recipeStep,int position){
            recipe_step.setText(recipeStep.getShortDescription());
            this.recipeStep = recipeStep;
            this.position = position;
        }
    }
}
