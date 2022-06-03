package lildwagz.com.phonefinder.data.remote.response


import com.google.gson.annotations.SerializedName

data class SpecX(
    @field:SerializedName("key")
    val key: String,
    @field:SerializedName("val")
    val valX: List<String>
)