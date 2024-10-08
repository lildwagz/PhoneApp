package lildwagz.com.phonefinder.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import lildwagz.com.phonefinder.data.local.dao.PhoneDao
import lildwagz.com.phonefinder.data.local.model.PhoneModel

@Database(entities = [PhoneModel::class], version = 13, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun phoneDao() : PhoneDao

}