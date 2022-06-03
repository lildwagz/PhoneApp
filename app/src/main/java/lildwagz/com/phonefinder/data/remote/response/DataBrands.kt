package lildwagz.com.phonefinder.data.remote.response


import com.google.gson.annotations.SerializedName

data class DataBrands(
    @field:SerializedName("brand_id")
    val brandId: Int,
    @field:SerializedName("brand_name")
    val brandName: String,
    @field:SerializedName("brand_slug")
    val brandSlug: String,
    @field:SerializedName("detail")
    val detail: String,
    @field:SerializedName("device_count")
    val deviceCount: Int
)