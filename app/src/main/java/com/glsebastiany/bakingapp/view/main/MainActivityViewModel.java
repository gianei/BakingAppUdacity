package com.glsebastiany.bakingapp.view.main;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.glsebastiany.bakingapp.repository.RecipesRepository;
import com.glsebastiany.bakingapp.repository.model.Recipe;
import com.glsebastiany.bakingapp.util.Util;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


public class MainActivityViewModel extends AndroidViewModel {

    @Inject
    RecipesRepository recipesRepository;
    private Disposable subscription = null;
    private MutableLiveData<List<Recipe>> recipes = new MutableLiveData<>();

    public MainActivityViewModel(Application application) {
        super(application);
        Util.getApplicationComponent(application).inject(this);
    }

    public LiveData<List<Recipe>> getRecipes() {
        if (recipes.getValue() == null) {
            loadRecipes();
        }
        return recipes;
    }

    private void loadRecipes() {
        subscription = recipesRepository
                .getRecipes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> recipes.setValue(result),
                        Timber::e
                );
    }

    @Override
    public void onCleared() {
        if (subscription != null && !subscription.isDisposed()) {
            subscription.dispose();
        }
    }

}
