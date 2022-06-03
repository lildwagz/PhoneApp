package lildwagz.com.phonefinder.data.remote.response


import com.google.gson.annotations.SerializedName

data class ResponseTopByInterest(
    @field:SerializedName("data")
    val `data`: TopByInterest,
    @field:SerializedName("status")
    val status: Boolean
)