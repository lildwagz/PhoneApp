package lildwagz.com.phonefinder.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="phone")
class PhoneModel (
    @PrimaryKey
    val slug: String ,
    @field:ColumnInfo(name = "phone_name")
    val phone_name: String,
    @field:ColumnInfo(name = "image")
    val image : String
)