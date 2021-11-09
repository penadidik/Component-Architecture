package info.penadidik.architecturecomponent.third

import info.penadidik.architecturecomponent.third.data.NetworkService.Companion.getService
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import io.reactivex.disposables.CompositeDisposable
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import info.penadidik.architecturecomponent.third.data.*

class ThirdViewModel : ViewModel() {

    private val networkService = NetworkService.getService()
    var newsList: LiveData<PagedList<News>>
    private val compositeDisposable = CompositeDisposable()
    private val pageSize = 5
    private val newsDataSourceFactory: NewsDataSourceFactory =
        NewsDataSourceFactory(compositeDisposable, networkService)

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setEnablePlaceholders(false)
            .build()
        newsList = LivePagedListBuilder(newsDataSourceFactory, config).build()
    }


    fun getState(): LiveData<State> = Transformations.switchMap(
        newsDataSourceFactory.newsDataSourceLiveData,
        NewsDataSource::state
    )

    fun retry() {
        newsDataSourceFactory.newsDataSourceLiveData.value?.retry()
    }

    fun listIsEmpty(): Boolean {
        return newsList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}