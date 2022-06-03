package lildwagz.com.phonefinder.data.remote.response


import com.google.gson.annotations.SerializedName

data class DataPhonesResponse(
    @field:SerializedName("current_page")
    val currentPage: Int,
    @field:SerializedName("last_page")
    val lastPage: Int,
    @field:SerializedName("phones")
    val phones: List<DataListPhonesBrand>,
    @field:SerializedName("title")
    val title: String
)