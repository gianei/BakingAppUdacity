package com.glsebastiany.bakingapp.view.main


import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.glsebastiany.bakingapp.repository.RecipesRepository
import com.glsebastiany.bakingapp.repository.model.Recipe
import com.glsebastiany.bakingapp.util.Util
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject


class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    @Inject lateinit var recipesRepository: RecipesRepository

    init {
        Util.getApplicationComponent(application).inject(this)
    }

    private var subscription: Disposable? = null

    private val recipes: MutableLiveData<List<Recipe>> = MutableLiveData()

    fun getRecipes(): LiveData<List<Recipe>> {
        if (recipes.value == null) loadRecipes()
        return recipes
    }

    private fun loadRecipes() {
        subscription = recipesRepository
                .getRecipes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            recipes.value = result
                        },
                        Timber::e
                )
    }

    override fun onCleared() {
        if (subscription?.isDisposed == false) subscription?.dispose()
    }

}
