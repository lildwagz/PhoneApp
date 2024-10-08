package lildwagz.com.phonefinder.data.local

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides

@Module
object DatabasePhone {
    @Provides
    fun provideDatabase(appContext: Context): LocalDatabase {
        return Room
            .databaseBuilder(
                appContext.applicationContext,
                LocalDatabase::class.java,
                "phonedb"
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideMangaDao(db : LocalDatabase) =
        db.phoneDao()
}