package lildwagz.com.phonefinder.data.remote.response


import com.google.gson.annotations.SerializedName

data class Data(
    @field:SerializedName("phones")
    val phones: List<Phone>,
    @field:SerializedName("title")
    val title: String
)