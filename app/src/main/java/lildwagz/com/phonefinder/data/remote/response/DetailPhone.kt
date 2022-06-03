package lildwagz.com.phonefinder.data.remote.response


import com.google.gson.annotations.SerializedName

data class DetailPhone(
    @field:SerializedName("data")
    val `data`: DataDetail,
    @field:SerializedName("status")
    val status: Boolean
)