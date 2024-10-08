package lildwagz.com.phonefinder.data.remote.response
import com.google.gson.annotations.SerializedName

data class ResultListBrands<T>(
    @field:SerializedName(value = "results", alternate = ["data"])
    val results: List<T> = listOf()
)