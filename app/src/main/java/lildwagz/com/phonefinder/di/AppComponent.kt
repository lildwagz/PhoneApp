package lildwagz.com.phonefinder.di

import android.app.Activity
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import lildwagz.com.phonefinder.data.local.DatabasePhone
import lildwagz.com.phonefinder.data.remote.PhoneApi
import lildwagz.com.phonefinder.ui.additional.AdditionalFragment
import lildwagz.com.phonefinder.ui.additional.ListBrandsFragment
import lildwagz.com.phonefinder.ui.detail.DetailFragment
import lildwagz.com.phonefinder.ui.favorite.FavoriteFragment
import lildwagz.com.phonefinder.ui.home.HomeFragment
import lildwagz.com.phonefinder.ui.search.SearchFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [PhoneApi::class, ViewModelModule::class,DatabasePhone::class])
interface AppComponent {
    fun inject(activity: Activity)
    fun inject(fragment : HomeFragment)
    fun inject(fragment : DetailFragment)
    fun inject(fragment : FavoriteFragment)
    fun inject(fragment : SearchFragment)
    fun inject(fragment : AdditionalFragment)
    fun inject(fragment : ListBrandsFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance appContext: Context): AppComponent
    }
}