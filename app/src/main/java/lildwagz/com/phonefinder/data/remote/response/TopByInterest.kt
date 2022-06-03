package lildwagz.com.phonefinder.data.remote.response


import com.google.gson.annotations.SerializedName

data class TopByInterest(
    @field:SerializedName("phones")
    val phones: List<DataTopByInterest>,
    @field:SerializedName("title")
    val title: String
)