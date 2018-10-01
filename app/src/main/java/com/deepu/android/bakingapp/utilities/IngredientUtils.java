package com.deepu.android.bakingapp.utilities;

import com.deepu.android.bakingapp.models.Ingredient;

import java.util.List;

public class IngredientUtils {

    public static String convertToString(List<Ingredient> ingredients){
        StringBuilder builder = new StringBuilder();
        String[] ingredientsArray = new String[ingredients.size()];
        for(int i =0; i<ingredients.size();i++){
            Ingredient ingredient = ingredients.get(i);
            builder.append(ingredient.getName());
            builder.append("(");
            builder.append(ingredient.getQuantity()+" ");
            builder.append(ingredient.getMeasure());
            builder.append(")");
            builder.append("\n");
        }
        return builder.toString();
    }
}