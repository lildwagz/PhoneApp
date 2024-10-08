package lildwagz.com.phonefinder.data.remote

import android.util.Log
import lildwagz.com.phonefinder.data.remote.api.Services
import lildwagz.com.phonefinder.data.remote.response.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RemoteDataSource  @Inject constructor(
    private val phoneServices : Services
){
    private lateinit var latestResponsePhone: List<Phone>
    private lateinit var topByFansResponsePhone: List<DataTopByFans>
    private lateinit var searchResponsePhone: List<Phone>
    private lateinit var resultBrands: List<DataBrands>
    private lateinit var resultPhoneListBrands : List<DataListPhonesBrand>
    private lateinit var  resultTopByInterest : List<DataTopByInterest>



    suspend fun getLatestPhone() : List<Phone>{
        val api = phoneServices.getLatestPhone()
        latestResponsePhone = api.data.phones

        return latestResponsePhone
    }

    suspend fun getTopByFans() : List<DataTopByFans>{
        val api = phoneServices.getTopByFans()
        topByFansResponsePhone = api.data.phones

        return topByFansResponsePhone
    }


    suspend fun searchPhone(query : String) : List<Phone>{
        val api = phoneServices.searchPhone(query)
        searchResponsePhone = api.data.phones
        Log.d("datakses",api.data.phones[0].phoneName)
        return searchResponsePhone
    }

    suspend fun getDetailPhone(phonekey : String) : DataDetail{
        val api = phoneServices.getDetailPhone(phonekey).data

        Log.d("datamasuk", api.toString())
        return api
    }

    suspend fun getBrands() : List<DataBrands>{
        val api = phoneServices.getBrands()
        if(api.status){
            resultBrands = api.data

        }

        return resultBrands

    }

    suspend fun getListPhonesByBrand(brand : String) : List<DataListPhonesBrand>{
        val api = phoneServices.getPhoneByBrands(brand)
        if (api.status){
            resultPhoneListBrands = api.data.phones
        }
        return resultPhoneListBrands

    }

    suspend fun getTopByInterest() : List<DataTopByInterest>{
        val api = phoneServices.getTopByInterest()
        if(api.status){
            resultTopByInterest = api.data.phones
        }

        return resultTopByInterest
    }
}