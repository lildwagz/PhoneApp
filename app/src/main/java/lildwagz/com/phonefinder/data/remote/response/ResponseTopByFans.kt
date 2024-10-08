package lildwagz.com.phonefinder.data.remote.response


import com.google.gson.annotations.SerializedName

data class ResponseTopByFans(
    @field:SerializedName("data")
    val `data`: TopByFans,
    @field:SerializedName("status")
    val status: Boolean
)