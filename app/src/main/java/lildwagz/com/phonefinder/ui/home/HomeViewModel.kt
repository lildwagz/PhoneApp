package lildwagz.com.phonefinder.ui.home

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import lildwagz.com.phonefinder.data.remote.RemoteDataSource
import lildwagz.com.phonefinder.utils.KeyState.TYPE_LATEST
import lildwagz.com.phonefinder.vo.ListState
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : ViewModel(){


    private var _listState = MutableLiveData<ListState>()
    var listState : LiveData<ListState> = _listState
    var mediaType: String = TYPE_LATEST

    fun getMediaList(tabType: String?) {
        mediaType = tabType ?: TYPE_LATEST
        getMedia(mediaType)
    }

    private fun getMedia(mediaType: String) = viewModelScope.launch(Dispatchers.IO) {
        when(mediaType){
            TYPE_LATEST ->{
                _listState.postValue(ListState.Loading)
                try {
                    _listState.postValue(ListState.Success(remoteDataSource.getLatestPhone()))
                } catch (e: Exception) {
                    _listState.postValue(ListState.Error(e))
                }
            }
        }

    }

    fun searchForPhone(query: String)  {
        _listState.postValue(ListState.Loading)
        viewModelScope.launch{
            try {
                _listState.postValue(ListState.Success(remoteDataSource.searchPhone(query)))
                Log.d("datakses", "masuk")

            } catch (e: Exception) {
                _listState.postValue(ListState.Error(e))
            }
        }



    }




}