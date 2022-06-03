package lildwagz.com.phonefinder.data.remote.response


import com.google.gson.annotations.SerializedName

data class ResponsePhone(
    @field:SerializedName("data")
    val `data`: Data,
    @field:SerializedName("status")
    val status: Boolean
)