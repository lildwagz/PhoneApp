package lildwagz.com.phonefinder.ui.additional

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import lildwagz.com.phonefinder.data.remote.RemoteDataSource
import lildwagz.com.phonefinder.utils.KeyState
import lildwagz.com.phonefinder.vo.*
import javax.inject.Inject

class AdditionalViewModel @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : ViewModel() {
    var mediaType: String = ""
    private var _brandsState = MutableLiveData<BrandsState>()
    var brandsState : LiveData<BrandsState> = _brandsState
    private var _brandsPhoneState = MutableLiveData<PhonesBrandsState>()
    var brandsPhoneState : LiveData<PhonesBrandsState> = _brandsPhoneState
    private var _topByFansState = MutableLiveData<TopByFansState>()
    var topByFansSate : LiveData<TopByFansState> = _topByFansState
    private var _topByInterest = MutableLiveData<TopByInterestState>()
    var topByInterest : LiveData<TopByInterestState> = _topByInterest


    fun getMedia(mediaType: String) = viewModelScope.launch(Dispatchers.IO){
        when(mediaType){
            KeyState.TYPE_LIST_BRANDS ->{
                _brandsState.postValue(BrandsState.Loading)
                try {
                    _brandsState.postValue(BrandsState.Success(remoteDataSource.getBrands()))
                } catch (e: Exception) {
                    _brandsState.postValue(BrandsState.Error(e))
                }
            }
            KeyState.TYPE_TOP_BY_FANS ->{
                _topByFansState.postValue(TopByFansState.Loading)
                try {
                    _topByFansState.postValue(TopByFansState.Success(remoteDataSource.getTopByFans()))
                } catch (e: Exception) {
                    _topByFansState.postValue(TopByFansState.Error(e))
                }
            }
            KeyState.TYPE_TOP_BY_INTEREST ->{
                _topByInterest.postValue(TopByInterestState.Loading)
                try{
                    _topByInterest.postValue(TopByInterestState.Success(remoteDataSource.getTopByInterest()))
                }catch (e : Exception){
                    _topByInterest.postValue(TopByInterestState.Error(e))
                }

            }

        }
    }

    fun getPhonesByBrands(brand : String) = viewModelScope.launch(Dispatchers.IO) {
        _brandsPhoneState.postValue(PhonesBrandsState.Loading)
        try {
            _brandsPhoneState.postValue(PhonesBrandsState.Success(remoteDataSource.getListPhonesByBrand(brand)))

        }catch (e : Exception){
            _brandsPhoneState.postValue(PhonesBrandsState.Error(e))

        }
    }



}