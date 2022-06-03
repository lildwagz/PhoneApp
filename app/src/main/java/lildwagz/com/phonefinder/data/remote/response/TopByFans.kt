package lildwagz.com.phonefinder.data.remote.response


import com.google.gson.annotations.SerializedName

data class TopByFans(
    @field:SerializedName("phones")
    val phones: List<DataTopByFans>,
    @field:SerializedName("title")
    val title: String
)