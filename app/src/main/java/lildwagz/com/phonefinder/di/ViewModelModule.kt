package lildwagz.com.phonefinder.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import lildwagz.com.phonefinder.ui.ViewModelFactory
import lildwagz.com.phonefinder.ui.additional.AdditionalViewModel
import lildwagz.com.phonefinder.ui.detail.DetailViewModel
import lildwagz.com.phonefinder.ui.favorite.FavoriteFragment
import lildwagz.com.phonefinder.ui.favorite.FavoriteViewModel
import lildwagz.com.phonefinder.ui.home.HomeViewModel
import lildwagz.com.phonefinder.ui.search.SearchViewModel

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    internal abstract fun homeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    internal abstract fun detailViewModel(viewModel: DetailViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteViewModel::class)
    internal abstract fun favoriteViewModel(viewModel : FavoriteViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    internal abstract fun searchViewModel(viewModel: SearchViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AdditionalViewModel::class)
    internal abstract fun additionalViewModel(viewModel : AdditionalViewModel) : ViewModel

}