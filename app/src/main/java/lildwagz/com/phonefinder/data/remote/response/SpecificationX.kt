package lildwagz.com.phonefinder.data.remote.response


import com.google.gson.annotations.SerializedName

data class SpecificationX(
    @field:SerializedName("specs")
    val specs: List<SpecX>,
    @field:SerializedName("title")
    val title: String
)