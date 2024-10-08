package lildwagz.com.phonefinder

import android.app.Application
import lildwagz.com.phonefinder.di.AppComponent
import lildwagz.com.phonefinder.di.DaggerAppComponent

class PhoneApp :  Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}