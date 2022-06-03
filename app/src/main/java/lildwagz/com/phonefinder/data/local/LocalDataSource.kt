package lildwagz.com.phonefinder.data.local

import lildwagz.com.phonefinder.data.local.dao.PhoneDao
import lildwagz.com.phonefinder.data.local.model.PhoneModel
import lildwagz.com.phonefinder.data.remote.response.Phone
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val phoneDao : PhoneDao
){

    suspend fun addItem(phone: PhoneModel) = phoneDao.addPhoneToFav(phone)

    suspend fun getALlItem() = phoneDao.getAllPhone()


    suspend fun deletePhone(phoneModel: PhoneModel) = phoneDao.deleteFavorite(phoneModel)
}