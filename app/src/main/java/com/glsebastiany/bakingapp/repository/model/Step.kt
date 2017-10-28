package com.glsebastiany.bakingapp.repository.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.parceler.Parcel

@Parcel
class Step {

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("shortDescription")
    @Expose
    var shortDescription: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("videoURL")
    @Expose
    var videoURL: String? = null

    @SerializedName("thumbnailURL")
    @Expose
    var thumbnailURL: String? = null

}
