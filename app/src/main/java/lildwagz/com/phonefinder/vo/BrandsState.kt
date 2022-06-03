package lildwagz.com.phonefinder.vo

import lildwagz.com.phonefinder.data.remote.response.Brands
import lildwagz.com.phonefinder.data.remote.response.DataBrands
import lildwagz.com.phonefinder.data.remote.response.Phone

sealed class BrandsState {
    object Loading : BrandsState()
    object Empty : BrandsState()
    class Success(val payload: List<DataBrands>) : BrandsState()
    class Error(val error: Throwable) : BrandsState()
}
