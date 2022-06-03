package lildwagz.com.phonefinder.data.remote.response


import com.google.gson.annotations.SerializedName

data class Brands(
    @field:SerializedName("data")
    val `data`: List<DataBrands>,
    @field:SerializedName("status")
    val status: Boolean
)