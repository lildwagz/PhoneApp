package lildwagz.com.phonefinder.data.remote.response


import com.google.gson.annotations.SerializedName

data class DataDetail(
    @field:SerializedName("brand")
    val brand: String,
    @field:SerializedName("dimension")
    val dimension: String,
    @field:SerializedName("os")
    val os: String,
    @field:SerializedName("phone_images")
    val phoneImages: List<String>,
    @field:SerializedName("phone_name")
    val phoneName: String,
    @field:SerializedName("release_date")
    val releaseDate: String,
    @field:SerializedName("specifications")
    val specifications: List<SpecificationX>,
    @field:SerializedName("storage")
    val storage: String,
    @field:SerializedName("thumbnail")
    val thumbnail: String
)