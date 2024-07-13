package com.ibrahim.utility.scopes

import com.ibrahim.utility.dispatchers.DefaultDispatcher
import com.ibrahim.utility.dispatchers.IoDispatcher
import com.ibrahim.utility.dispatchers.MainDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ScopesModule {

    @MainScope
    @Singleton
    @Provides
    fun providesMainCoroutineScope(
        @MainDispatcher mainDispatcher: CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(SupervisorJob() + mainDispatcher)

    @IoScope
    @Singleton
    @Provides
    fun provideIOScope(@IoDispatcher ioDispatcher: CoroutineDispatcher): CoroutineScope =
        CoroutineScope(
            SupervisorJob() + ioDispatcher
        )

    @DefaultScope
    @Singleton
    @Provides
    fun provideDefaultScope(@DefaultDispatcher defaultDispatcher: CoroutineDispatcher): CoroutineScope =
        CoroutineScope(
            SupervisorJob() + defaultDispatcher
        )
}