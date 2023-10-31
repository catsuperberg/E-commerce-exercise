package dev.catsuperberg.e_commerce_exercise.client

import android.app.Application
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import coil.Coil
import coil.ImageLoader
import coil.disk.DiskCache
import coil.request.CachePolicy
import dev.catsuperberg.e_commerce_exercise.client.data.repository.item.IItemMapper
import dev.catsuperberg.e_commerce_exercise.client.data.repository.item.ItemPagingSource
import dev.catsuperberg.e_commerce_exercise.client.data.repository.item.PlaceholderItemMapper
import dev.catsuperberg.e_commerce_exercise.client.data.repository.order.IOrderEndPoint
import dev.catsuperberg.e_commerce_exercise.client.data.repository.order.IOrderMapper
import dev.catsuperberg.e_commerce_exercise.client.data.repository.order.OrderEndPoint
import dev.catsuperberg.e_commerce_exercise.client.data.repository.order.OrderMapper
import dev.catsuperberg.e_commerce_exercise.client.domain.service.ClearCacheUriImageAccess
import dev.catsuperberg.e_commerce_exercise.client.domain.service.IItemMessageComposer
import dev.catsuperberg.e_commerce_exercise.client.domain.service.IUriImageAccess
import dev.catsuperberg.e_commerce_exercise.client.domain.service.ItemMessageComposer
import dev.catsuperberg.e_commerce_exercise.client.domain.usecase.IItemDetailsSender
import dev.catsuperberg.e_commerce_exercise.client.domain.usecase.IOrderRegistration
import dev.catsuperberg.e_commerce_exercise.client.domain.usecase.IPaginatedItemProvider
import dev.catsuperberg.e_commerce_exercise.client.domain.usecase.ItemDetailsSender
import dev.catsuperberg.e_commerce_exercise.client.domain.usecase.OrderRegistration
import dev.catsuperberg.e_commerce_exercise.client.domain.usecase.PaginatedItemProvider
import dev.catsuperberg.e_commerce_exercise.client.presentation.view.model.main.IMainViewModel
import dev.catsuperberg.e_commerce_exercise.client.presentation.view.model.main.MainViewModel
import dev.catsuperberg.e_commerce_exercise.client.presentation.view.model.order.form.IOrderFormViewModel
import dev.catsuperberg.e_commerce_exercise.client.presentation.view.model.order.form.OrderFormViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.core.parameter.parametersOf
import org.koin.dsl.bind
import org.koin.dsl.module

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()

        val imageLoader = ImageLoader.Builder(this)
            .diskCache {
                DiskCache.Builder()
                    .directory(this.cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.5)
                    .build()
            }
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .respectCacheHeaders(false)
            .build()

        Coil.setImageLoader(imageLoader)

        val mainModule = module {
            factoryOf(::PlaceholderItemMapper) bind IItemMapper::class
            factory { PagingConfig(pageSize = ItemPagingSource.pageSize) } bind PagingConfig::class
            factoryOf(::ItemPagingSource) bind PagingSource::class

            factory {
                PaginatedItemProvider(
                    get { parametersOf(ItemPagingSource.itemQuery) },
                    get { parametersOf(ItemPagingSource.availableItemQuery) },
                    get()
                )
            } bind IPaginatedItemProvider::class
            factoryOf(::ClearCacheUriImageAccess) bind IUriImageAccess::class
            factoryOf(::ItemMessageComposer) bind IItemMessageComposer::class
            factoryOf(::ItemDetailsSender) bind IItemDetailsSender::class


            factoryOf(::OrderMapper) bind IOrderMapper::class
            factoryOf(::OrderEndPoint) bind IOrderEndPoint::class
            factoryOf(::OrderRegistration) bind IOrderRegistration::class

            factory { OrderFormViewModel(get(), get(), get()) } bind IOrderFormViewModel::class
            factory { MainViewModel(get(), get(), get()) } bind IMainViewModel::class
        }

        startKoin {
            androidLogger()
            androidContext(this@MainApp)
            modules(mainModule)
        }
    }
}