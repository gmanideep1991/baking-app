package com.deepu.android.bakingapp.services;

import com.deepu.android.bakingapp.models.Recipe;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface BakingService {

    @GET("/topher/2017/May/59121517_baking/baking.json")
    Observable<List<Recipe>> getRecipes();

}
