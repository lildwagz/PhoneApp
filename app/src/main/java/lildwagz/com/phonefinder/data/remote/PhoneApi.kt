package lildwagz.com.phonefinder.data.remote

import dagger.Module
import dagger.Provides
import lildwagz.com.phonefinder.data.remote.api.Services
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
object PhoneApi {
    private const val BASE_URL = "http://api-mobilespecs.azharimm.site/"

    @Provides
    fun provideMangaRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    fun provideMangaService(retrofit: Retrofit): Services =
        retrofit.create(Services::class.java)
}