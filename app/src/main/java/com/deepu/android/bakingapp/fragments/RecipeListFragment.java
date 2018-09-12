package com.deepu.android.bakingapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.deepu.android.bakingapp.R;
import com.deepu.android.bakingapp.adapters.RecipeListAdapter;
import com.deepu.android.bakingapp.models.Recipe;
import com.deepu.android.bakingapp.network.RetrofitClientInstance;
import com.deepu.android.bakingapp.services.BakingService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class RecipeListFragment extends Fragment {

    @BindView(R.id.recipelist_recyclerview)
    RecyclerView recipe_list;

    LinearLayoutManager recipeListManager = new LinearLayoutManager(getContext());
    private static final String SAVED_SCROLL_POSITION = "SAVED_SCROLL_POSITION";

    public RecipeListFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recipe_list,container,false);
        ButterKnife.bind(this,rootView);
        populateRecipes();
        return rootView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(SAVED_SCROLL_POSITION)) {
            int position = savedInstanceState.getInt(SAVED_SCROLL_POSITION);
        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        int position = recipeListManager.findLastVisibleItemPosition();
        outState.putInt(SAVED_SCROLL_POSITION, position);
        super.onSaveInstanceState(outState);
    }

    public void populateRecipes(){
        BakingService service = RetrofitClientInstance.getRetrofitInstance().create(BakingService.class);
        Observable<List<Recipe>> call = service.getRecipes();

        call.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<List<Recipe>>() {
            @Override
            public void onNext(List<Recipe> recipes) {
                populateAdapter(recipes);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void populateAdapter(List<Recipe> recipes){
        if(recipes.isEmpty()){
            Toast.makeText(getContext(),"Recipes are empty",Toast.LENGTH_LONG).show();
        }
        else{
            RecipeListAdapter recipeListAdapter = new RecipeListAdapter(recipes);
            recipe_list.setAdapter(recipeListAdapter);
            recipe_list.setLayoutManager(recipeListManager);
        }
    }
}

