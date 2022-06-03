package lildwagz.com.phonefinder.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import lildwagz.com.phonefinder.data.remote.RemoteDataSource
import lildwagz.com.phonefinder.vo.ListState
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : ViewModel(){
    private val _searchState =  MutableLiveData<ListState>()
    var searchState : LiveData<ListState> = _searchState


    fun searchForPhone(query: String)  {
        _searchState.postValue(ListState.Loading)
        viewModelScope.launch(){
            try {
                _searchState.postValue(ListState.Success(remoteDataSource.searchPhone(query)))
                Log.d("datakses", "stateTry $query")

            } catch (e: Exception) {
                _searchState.postValue(ListState.Error(e))
            }
        }



    }
}