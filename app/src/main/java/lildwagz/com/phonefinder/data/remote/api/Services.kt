package lildwagz.com.phonefinder.data.remote.api

import lildwagz.com.phonefinder.data.remote.response.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Services {

    @GET("/v2/latest")
    suspend fun getLatestPhone() : ResponsePhone

    @GET("/v2/top-by-fans")
    suspend fun getTopByFans() : ResponseTopByFans

    @GET("/v2/{namePhone}")
    suspend fun getDetailPhone(
        @Path("namePhone") namePhone : String
    ) : DetailPhone

    @GET("/v2/search")
    suspend fun searchPhone(
        @Query("query") uery: String
    ) : ResponsePhone

    @GET("/v2/brands")
    suspend fun getBrands() : Brands

    @GET("/v2/brands/{brand}")
    suspend fun getPhoneByBrands(
        @Path("brand") brand : String
    ) : ResponsePhonesBrands

    @GET("/v2/top-by-interest")
    suspend fun getTopByInterest() : ResponseTopByInterest

}