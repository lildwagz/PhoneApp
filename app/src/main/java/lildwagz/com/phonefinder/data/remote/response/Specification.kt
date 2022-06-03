package lildwagz.com.phonefinder.data.remote.response


import com.google.gson.annotations.SerializedName

data class Specification(
    @field:SerializedName("specs")
    val specs: List<Spec>,
    @field:SerializedName("title")
    val title: String
)