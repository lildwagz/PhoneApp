package lildwagz.com.phonefinder.vo

import lildwagz.com.phonefinder.data.remote.response.DataTopByInterest

sealed class TopByInterestState{
    object Loading : TopByInterestState()
    object Empty : TopByInterestState()
    class Success(val payload: List<DataTopByInterest>) : TopByInterestState()
    class Error(val error: Throwable) : TopByInterestState()
}
