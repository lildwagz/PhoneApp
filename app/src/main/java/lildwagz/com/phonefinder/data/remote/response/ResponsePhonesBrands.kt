package lildwagz.com.phonefinder.data.remote.response


import com.google.gson.annotations.SerializedName

data class ResponsePhonesBrands(
    @field:SerializedName("data")
    val `data`: DataPhonesResponse,
    @field:SerializedName("status")
    val status: Boolean
)