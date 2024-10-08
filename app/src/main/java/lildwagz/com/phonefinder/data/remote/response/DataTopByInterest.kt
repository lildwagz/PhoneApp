package lildwagz.com.phonefinder.data.remote.response


import com.google.gson.annotations.SerializedName

data class DataTopByInterest(
    @field:SerializedName("detail")
    val detail: String,
    @field:SerializedName("hits")
    val hits: Int,
    @field:SerializedName("phone_name")
    val phoneName: String,
    @field:SerializedName("slug")
    val slug: String
)