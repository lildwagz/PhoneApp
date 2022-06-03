package lildwagz.com.phonefinder.vo

import lildwagz.com.phonefinder.data.remote.response.Phone

sealed class SearchState {
    object Loading : SearchState()
    object Empty : SearchState()
    class Success(val payload: List<Phone>) : SearchState()
    class Error(val error: Throwable) : SearchState()
}