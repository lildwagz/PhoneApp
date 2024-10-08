package lildwagz.com.phonefinder.data.remote.response


import com.google.gson.annotations.SerializedName

data class Phone(
    @field:SerializedName("detail")
    val detail: String,
    @field:SerializedName("image")
    val image: String,
    @field:SerializedName("phone_name")
    val phoneName: String,
    @field:SerializedName("slug")
    val slug: String

    )