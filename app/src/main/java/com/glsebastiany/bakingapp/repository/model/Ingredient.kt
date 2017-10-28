package com.glsebastiany.bakingapp.repository.model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.parceler.Parcel

@Parcel
class Ingredient {

    @SerializedName("quantity")
    @Expose
    var quantity: Float? = null

    @SerializedName("measure")
    @Expose
    var measure: String? = null

    @SerializedName("ingredient")
    @Expose
    var ingredient: String? = null

}





