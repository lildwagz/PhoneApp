package lildwagz.com.phonefinder.ui.detail

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import lildwagz.com.phonefinder.data.local.LocalDataSource
import lildwagz.com.phonefinder.data.local.model.PhoneModel
import lildwagz.com.phonefinder.data.remote.RemoteDataSource
import lildwagz.com.phonefinder.vo.DetailsState
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) :
    ViewModel() , LifecycleObserver {
    var detailsState = MutableLiveData<DetailsState>(DetailsState.Loading)



    fun getMediaDetails(phoneKey: String) = viewModelScope.launch  {
        detailsState.postValue(DetailsState.Loading)
        try{
            detailsState.postValue(DetailsState.Success(remoteDataSource.getDetailPhone(phoneKey)))

        }catch (e: Exception) {
            detailsState.postValue(DetailsState.Error(e))
        }

    }

    fun addtoFavorite(phone: PhoneModel) = viewModelScope.launch {
        localDataSource.addItem(phone)

    }

    fun deletFromFavorite(phone : PhoneModel) = viewModelScope.launch{
        localDataSource.deletePhone(phone)
    }

}