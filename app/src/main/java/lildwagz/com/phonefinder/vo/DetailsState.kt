package lildwagz.com.phonefinder.vo


import lildwagz.com.phonefinder.data.remote.response.DataDetail

sealed class DetailsState {
    object Loading : DetailsState()
    class Success(val payload: DataDetail) : DetailsState()
    class Error(val error: Throwable) : DetailsState()
}
