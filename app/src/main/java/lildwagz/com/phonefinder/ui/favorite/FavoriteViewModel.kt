package lildwagz.com.phonefinder.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import lildwagz.com.phonefinder.data.local.LocalDataSource
import lildwagz.com.phonefinder.data.local.model.PhoneModel
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
    private val localDataSource: LocalDataSource
): ViewModel() {
    private val _favorite = MutableLiveData<List<PhoneModel>>()
    val favorite: LiveData<List<PhoneModel>> = _favorite


    fun removePhone(phone :PhoneModel) = viewModelScope.launch{
        localDataSource.deletePhone(phone)
    }


    fun getFavoriteData() = viewModelScope.launch{
        viewModelScope.launch {
            _favorite.postValue(localDataSource.getALlItem())
        }

    }


}