package lildwagz.com.phonefinder.vo

import lildwagz.com.phonefinder.data.remote.response.Phone

sealed class ListState {
    object Loading : ListState()
    object Empty : ListState()
    class Success(val payload: List<Phone>) : ListState()
    class Error(val error: Throwable) : ListState()
}