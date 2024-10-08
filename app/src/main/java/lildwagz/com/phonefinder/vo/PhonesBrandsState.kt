package lildwagz.com.phonefinder.vo

import lildwagz.com.phonefinder.data.remote.response.DataDetail
import lildwagz.com.phonefinder.data.remote.response.DataListPhonesBrand

sealed class PhonesBrandsState {
    object Loading : PhonesBrandsState()
    class Success(val payload: List<DataListPhonesBrand>) : PhonesBrandsState()
    class Error(val error: Throwable) : PhonesBrandsState()
}
