package lildwagz.com.phonefinder.data.local.dao

import androidx.room.*
import lildwagz.com.phonefinder.data.local.model.PhoneModel


@Dao
interface PhoneDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPhoneToFav(phone : PhoneModel)

    @Query("select * from phone")
    suspend fun getAllPhone() : List<PhoneModel>


    @Delete
    suspend fun deleteFavorite(phoneModel: PhoneModel)
}