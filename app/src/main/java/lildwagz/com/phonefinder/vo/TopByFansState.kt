package lildwagz.com.phonefinder.vo

import lildwagz.com.phonefinder.data.remote.response.DataTopByFans

sealed class TopByFansState {
    object Loading : TopByFansState()
    object Empty : TopByFansState()
    class Success(val payload: List<DataTopByFans>) : TopByFansState()
    class Error(val error: Throwable) : TopByFansState()
}