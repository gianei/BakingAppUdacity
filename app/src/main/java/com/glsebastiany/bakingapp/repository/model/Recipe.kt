package com.glsebastiany.bakingapp.repository.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.parceler.Parcel

@Parcel
class Recipe {

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("ingredients")
    @Expose
    var ingredients: List<Ingredient>? = null

    @SerializedName("steps")
    @Expose
    var steps: List<Step>? = null

    @SerializedName("servings")
    @Expose
    var servings: Int? = null

    @SerializedName("image")
    @Expose
    var image: String? = null

}
